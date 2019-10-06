package models

import play.api.libs.json.Json


class Tshirt(var id: Int = 0, var color: String = "", var pic_url: String = "", var price: Int = 0) {

  var tShirts: Set[Tshirt] = Set()

  def add(tshirt: Tshirt): Unit ={
    tShirts+=tshirt
  }

  def getTshirts(): Set[Tshirt] ={
    return tShirts
  }

  def getAll(): List[Tshirt] ={
    return Tshirt.getAllTshirts().toList
  }
}

// singleton object
object Tshirt {
  var tshirts = new Tshirt()
  tshirts.add(new Tshirt(1, "red", "https://live.staticflickr.com/5246/5276588069_ce5ab13b09.jpg", 20))
  tshirts.add(new Tshirt(2, "yellow", "https://live.staticflickr.com/654/21474568892_0369bd9f43.jpg", 25))
  tshirts.add(new Tshirt(3, "blue", "https://live.staticflickr.com/8086/8454636463_8ff5034d2d.jpg", 30))

  def getAllTshirts(): Set[Tshirt] ={
    return tshirts.getTshirts()
  }
}