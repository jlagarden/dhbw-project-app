package com.project.sparkstreaming

// spark
import org.apache.spark._
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

// Data structures
import scala.collection.immutable.Queue

// parsing Json
import net.liftweb.json._
import net.liftweb.json.JsonAST._
import net.liftweb.json.Serialization.{read, write}

// kafka Producer
import java.util.Properties
import kafka.producer.ProducerConfig
import kafka.producer.Producer
import kafka.producer.KeyedMessage

// Datastructures used to Store all Informationt we want to analyse
case class ERPData(customerNumber: String, materialNumber: String, orderNumber: String, timeStamp: String)
case class ProdData(val value: Any, val status: String, val itemName : String, val timestamp: Int)
case class SpecData(val em1: Double, val em2: Double,  val a1: Double,val a2: Double, val b2: Double, val b1: Double, val overallStatus: String, val ts_start: Double, val ts_stop: Double)
case class ItemData(val erpData: ERPData, val specData: SpecData, val prodData: List[ProdData])

// Result Datastructures
case class CustomerData(val number: Int, val amountorders: Int, val materialnumber: Int, val avgrejects: Float, val amountorderedmaterial: Int)

object App {

  // parse String to JValue
  def asJson(str: String) = {
    val parsed = parse(str)
    parsed
  }

  // get ERP Data only Input Data in JValue Format
  def getErpData(input: JValue) = {
    val tmp = (input \ "erpData")
    val customerNumber = (tmp \ "customerNumber").asInstanceOf[JString].s
    val materialNumber = (tmp \ "materialNumber").asInstanceOf[JString].s
    val orderNumber = (tmp \ "orderNumber").asInstanceOf[JString].s
    val timeStamp = (tmp \ "timeStamp").asInstanceOf[JString].s
    new ERPData(customerNumber, materialNumber, orderNumber, timeStamp)
  }

  // get spectral analysis Data only Input Data in JValue Format
  def getSpecData(input: JValue) = {
    val tmp = (input \ "specData")
    val em1 = (tmp \ "em1").asInstanceOf[JDouble].num
    val em2 = (tmp \ "em2").asInstanceOf[JDouble].num
    val a1 = (tmp \ "a1").asInstanceOf[JDouble].num
    val a2 = (tmp \ "a2").asInstanceOf[JDouble].num
    val b2 = (tmp \ "b2").asInstanceOf[JDouble].num
    val b1 = (tmp \ "b1").asInstanceOf[JDouble].num
    val overallStatus = (tmp \ "overallStatus").asInstanceOf[JString].s
    val ts_start = (tmp \ "ts_start").asInstanceOf[JDouble].num
    val ts_stop = (tmp \ "ts_stop").asInstanceOf[JDouble].num
    new SpecData(em1,em2,a1,a2,b2,b1,overallStatus,ts_start,ts_stop)
  }

  // get Production Data only Input Data in JValue Format
  def getProdDataList(input: JValue): List[ProdData] = {
    val tmp: List[JValue] = (input \ "prodData").asInstanceOf[JArray].arr
    var l: List[ProdData] = tmp.map(tmpdata => getProdData(tmpdata))
    l
  }

  // parse Single ProdData items
  def getProdData(input: JValue): ProdData = {
    var value: Any = false;
    if ((input \ "value").isInstanceOf[JString])
      value = (input \ "value").asInstanceOf[JString].s
    if ((input \ "value").isInstanceOf[JBool])
      value = (input \ "value").asInstanceOf[JBool].value
    if ((input \ "value").isInstanceOf[JInt])
      value = (input \ "value").asInstanceOf[JInt].num
    val status = (input \ "status").asInstanceOf[JString].s
    val itemName = (input \ "itemName").asInstanceOf[JString].s
    val timestamp = (input \ "timestamp").asInstanceOf[JInt].num.intValue
    new ProdData(value,status,itemName,timestamp)
  }

  // main Method, setup and start SparkStreaming Application
  def main(args: Array[String]) {
    // setup SparkContext
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)

    // needed for converting JSON Objects
    implicit val formats = DefaultFormats

    // configure Kafka Broker
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "example",
      "auto.offset.reset" -> "latest",
      "num.stream.threads" -> (1: java.lang.Integer),
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // create StreamingContext from SparkContext
    val ssc = new StreamingContext(sc, new Duration(120000))

    // conntect to Kafka using a DirectKafka Stream
    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, Subscribe[String, String](topics, kafkaParams))

    // create different streams for analysing All ProdData, SpecData and ErpData seperatly
    val jsvals = stream.map(record => asJson(record.value))
    val erpstream = jsvals.map(record => getErpData(record))
    val specstream = jsvals.map(record => getSpecData(record))
    val prodstream = jsvals.map(record => getProdDataList(record)).flatMap(x => x)

    // print out the streams (for debugging)
    // specstream.print()
    // erpstream.print()
    // prodstream.print()

    // aggregate Data By Customer
    val stream1 = erpstream.map(x => ((x.customerNumber, x.materialNumber), 1)).reduceByKey(_ + _).map(x => (x._1._1,(x._1._2,x._2)))
    val stream2 = erpstream.map(x => (x.customerNumber, 1)).reduceByKey(_ + _)
    // join both streams together
    val cresult = stream2.join(stream1,1).map(x => CustomerData(x._1.toInt,x._2._2._2,x._2._2._1.toInt,0,x._2._1))
    cresult.print()

    cresult.foreachRDD(item => {
      item.foreach(message => {
        val kafkaSink = new KafkaSink()
        implicit val formats = Serialization.formats(NoTypeHints)
        kafkaSink.send(write(message))
      })
    })

    // start StreamingContext and wait for StreamingContext to Terminate
    ssc.start()
    ssc.awaitTermination()
  }
}

class KafkaSink extends Serializable {

  val props = new Properties()
  props.put("metadata.broker.list", "kafka:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  props.put("producer.type", "async")
  val config = new ProducerConfig(props)
  val producer = new Producer[String, String](config)

  def send(value: String): Unit = producer.send(new KeyedMessage[String, String]("customer", "kafka:9092", value))
}
