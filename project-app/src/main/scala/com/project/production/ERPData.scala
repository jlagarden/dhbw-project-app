package com.project.production

class ERPData(val customerNumber: String, val materialNumber: String, val orderNumber: String, val timeStamp: String) {
  override def toString =
    s"customerNumber: $customerNumber, materialNumber: $materialNumber, orderNumber: $orderNumber, timeStamp: $timeStamp"
}

object ERPData {
  //convert XML to ERPData
  def fromXml(node: scala.xml.Node):ERPData = {
    val customerNumber = (node \ "customerNumber").text
    val materialNumber = (node \ "materialNumber").text
    val orderNumber = (node \ "orderNumber").text
    val timeStamp = (node \ "timeStamp").text

    new ERPData(customerNumber, materialNumber, orderNumber, timeStamp)
  }
}
