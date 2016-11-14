package com.project.app

import com.project.production._
import com.project.consumer._

class Material(val number: Any, val amount: Int, val rejects : Int, val prodtime: Double) {

}

object Material {

    implicit val formats = DefaultFormats

    def apply(input: String) = parse(input).extract[Material]

}

//         number: 4728,
//         amount: 4,
//         rejects: 1,
//         prodtime: 22.4
