package com.example.testfinalnow.firebase

data class Orders(
    var Address: String ="",
    var Date: String= ""+com.google.firebase.Timestamp.now(),
    var ItemsList: String ="",
    var ItemsQuantity: Int= 0,
    var OrderId: Int= 0,
    var Price: Double= 0.0,
    var User: String ="",
    var Email: String="",
    var Phone: String=""
) {
}