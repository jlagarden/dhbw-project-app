package com.project.app

import com.project.production._
import com.project.consumer._

class Customer(val number: Any, val amountorders: Int, val materialnumber: Int, val avgrejects: Double, val amountorderedmaterial: Int) {

}

object Customer {
    implicit val formats = DefaultFormats
    def apply(input: String) = parse(input).extract[Customer]
}


//         number: 4717,
//         amountorders: 2,
//         materialnumber: 1757,
//         avgrejects: 0.3,
//         amountorderedmaterial: 21
