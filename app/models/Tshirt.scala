package models

class Tshirt(var id: Int, var color: String, var picture: String, var price: String) {
  var tShirts: Set[Tshirt] = Set()

  def add(tshirt: Tshirt): Unit ={
    tShirts+=tshirt
  }

}
