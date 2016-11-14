package com.project.app

import com.project.production._
import com.project.consumer._
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._


class Analysis() {
  var amount_in_prod = 0
  var speed_milling = 0.0
  var speed_drilling = 0.0
  var counter = 0
  var counter_rejects = 0
  var temperature_milling = 0.0
  var temperature_drilling = 0.0
  var current_action = Any
  var reject = 0
  var success = 0
  var customer = []
  var material = []

  def processMessage(input: Any) {
    input match {
      case x: ProdData -> {
        processProdData(x)
        jsonOutput()
      }
      case x: ERPData -> {
        processERPData(x)
        jsonOutput()
      }
      case x: SpecData -> {
        processSpecData(x)
        jsonOutput()
      }
    }
  }

  def processProdData(inp: ProdData) {
    inp match {
      case x: ProdData(false, _, "L1", _) -> {
        amount_in_prod = amount_in_prod + 1
        current_action = "" + x.itemName
      }
      case x: ProdData(false, _, "L2", _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(false, _, "L3", _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(false, _, "L4", _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(false, _,"L5",  _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(true, _, "L5", _) -> {
        amount_in_prod = amount_in_prod - 1
      }
      case x: ProdData(_, _, "MILLING", _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(_, _, "MILLING_SPEED", _) -> {
        speed_milling = x.value
      }
      case x: ProdData(_, _, "MILLING_HEAT", _) -> {
        temperature_milling = x.value
      }
      case x: ProdData(_, _, "DRILLING", _) -> {
        current_action = "" + x.itemName
      }
      case x: ProdData(_, _, "DRILLING_SPEED", _) -> {
        speed_drilling = x.value
      }
      case x: ProdData(_, _, "DRILLING_HEAT", _) -> {
        temperature_drilling = x.value
      }

      case ProdData(_,_,_,_) -> {}
    }
  }

  def processSpecData(inp: SpecData) {

  }

  def processERPData(inp: ERPData) {
    //ERPData: customerNumber, materialNumber, orderNumber, timeStamp
    customer.map(x => {
      if(x.number == inp.customerNumber) {

      } else {
        var newCustomer = new Customer(inp.customerNumber, 0, inp.materialNumber, 0, 0)
        customer +: newCustomer
      }
    }
  }

  def jsonOutput() {
    val sampleCustomer = [
      {
          number: 4717,
          amountorders: 2,
          materialnumber: 1757,
          avgrejects: 0.3,
          amountorderedmaterial: 21
      },
      {
          number: 4717,
          amountorders: 2,
          materialnumber: 1757,
          avgrejects: 0.3,
          amountorderedmaterial: 21
      },
      {
          number: 4717,
          amountorders: 2,
          materialnumber: 1757,
          avgrejects: 0.3,
          amountorderedmaterial: 21
      }
    ]

    val sampleMaterial = [
      {
          number: 4728,
          amount: 4,
          rejects: 1,
          prodtime: 22.4
      },
      {
          number: 6346,
          amount: 7,
          rejects: 5,
          prodtime: 17.4
      },
      {
          number: 4728,
          amount: 4,
          rejects: 1,
          prodtime: 22.4
      }
    ]

    val json =
      ("kpis" ->
        ("speed_milling"    -> speed_milling) ~
        ("speed_drilling"   -> speed_drilling) ~
        ("counter"          -> counter) ~
        ("counter_rejects"  -> counter_rejects) ~
        ("temperature_drilling" -> temperature_drilling) ~
        ("temperature_milling" -> temperature_milling) ~
        ("amount_in_prod" -> amount_in_prod) ~
        ("current_action" -> current_action)
      )
      ("dashboard_charts" ->
        ("donutchart" ->
          ("reject" -> reject) ~
          ("success" -> success)
        )
        ("materialchart" -> [
          ['x', '1476258417416', '1476258436796', '1476258446806', '1476258486826', '1476258536906', '1476258546936'],
          ['material1nr', 30, 200, 240, 400, 150, 250],
          ['material2nr(8235)', 130, 340, 200, 500, 250, 350]
        ])
      )
      ("material" -> sampleMaterial)
      ("customer" -> sampleCustomer)
      ("chartview" ->
        ("good_bad_materials" -> [
          ['material1', 'material2', 'material3', 'material4', 'material5', 'material6'],
          [
            ['good', 30, 20, 70, 40, 15, 50],
            ['bad', 50, 20, 10, 40, 15, 25]
          ]
        ])
      )
      )

    compact(render(json))
  }
}
