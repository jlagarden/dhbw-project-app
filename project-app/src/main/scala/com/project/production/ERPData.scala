package com.project.production

import scala.xml._

case class ERPData private (val customerNumber: String, val materialNumber: String, val orderNumber: String, val timeStamp: String)

object ERPData {
  //convert XML to ERPData
  def apply(input: String): ERPData = {
    val node = scala.xml.XML.loadString(input)
    val customerNumber = (node \ "customerNumber").text
    val materialNumber = (node \ "materialNumber").text
    val orderNumber = (node \ "orderNumber").text
    val timeStamp = (node \ "timeStamp").text

    ERPData(customerNumber, materialNumber, orderNumber, timeStamp)
  }
}
