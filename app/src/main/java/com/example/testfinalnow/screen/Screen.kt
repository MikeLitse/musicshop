package com.example.testfinalnow.screen

sealed class Screen(val route: String) {
    object Main: Screen(route="main/{id}")
    object Vinyl: Screen(route="vinyl/{id}")
    object CheckOut: Screen(route="checkout/{id}")
    object Cd: Screen(route="cd/{id}")
    object Cassete: Screen(route="cassete/{id}")
    object AddProduct: Screen(route="addproduct/{id}")
    object BuyProduct: Screen(route="buyproduct/{id}")
    object DeleteProduct: Screen(route="deleteproduct/{id}")
    object AllOrders: Screen(route="allorders/{id}")
    object EditProduct: Screen(route="editproduct/{id}")
    object StoreStatus: Screen(route="storestatus/{id}")
    object AddStore: Screen(route="addstore/{id}")
    object EditOrder: Screen(route="editorder/{id}")

}