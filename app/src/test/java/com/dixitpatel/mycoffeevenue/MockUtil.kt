package com.dixitpatel.mycoffeevenue

import com.dixitpatel.mycoffeevenue.model.*

object MockUtil {

  fun location() = Location("Unit 49 First Floor","Wembley Park Boulevard",51.55632269860604,
  -0.28291010681428796,1073, "HA9 0FD",
"GB",
  "Tokyngton",
  "Greater London",
  "United Kingdom",
  listOf(
  "Unit 49 First Floor (Wembley Park Boulevard)", "Tokyngton", "Greater London", "HA9 0FD", "United Kingdom",
  ),"k")

  fun category() = Category("4bf58dd8d48988d1e0931735",
  "Coffee Shop", Icon("https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_",
    ".png")
  )

  fun venue() = Venue("528490bf11d24bf0d288fb81",name = "Costa Coffee", location(), listOf(category()))

  fun listOfItem() = Item(venue())

  fun listOfGroup() = Group("Recommended Places","Recommended", listOf(listOfItem()))

  fun mockPlaces() = NearByPlaceResponse(
    meta = Meta(200,"600163e609586a1e9ccf4cf4","",""),
    response = Response("London",1, listOf(listOfGroup()))
  )

  fun mockNearByPlaceList() = listOf(mockPlaces())

}
