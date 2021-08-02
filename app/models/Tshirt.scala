package models

import play.api.libs.json.{ Json}

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
  // Overriding tostring method
  override def toString() : String = {

    "{id: " + Json.toJson(id) + ", " +
      "color: " + Json.toJson(color)+", "+"pic_url: "+Json.toJson(pic_url)+ ", "+"price: "+Json.toJson(price)+"}";
  }
}

// singleton object
object Tshirt {
  var tshirts = new Tshirt()
  tshirts.add(new Tshirt(1, "red", "https://live.staticflickr.com/5246/5276588069_ce5ab13b09.jpg", 20))
  tshirts.add(new Tshirt(2, "yellow", "https://live.staticflickr.com/65535/51352604889_8dec34bea2.jpg", 25))
  tshirts.add(new Tshirt(3, "blue", "https://live.staticflickr.com/65535/51352604879_b0a113f3d8_h.jpg", 30))

  def getAllTshirts(): Set[Tshirt] ={
    return tshirts.getTshirts()
  }
}