package com.example.testfinalnow


import androidx.lifecycle.ViewModel
import com.example.testfinalnow.db.Cart
import com.example.testfinalnow.db.Product
import com.example.testfinalnow.db.ProductDatabase

class ProductViewModel: ViewModel() {
    var productClickedImage= "" //image
    var productClickedId= -1
    var productClickedForDelete=-1
    var orderclicked=0

    fun addToCart(product: Product, productDatabase: ProductDatabase){
        product.stock= product.stock-1
        productDatabase.getProductDao().updateProduct(product)
        productDatabase.getProductDao().addToCart(Cart(0,product.name,product.price,product.id))
    }
    fun deleteProduct(product: Product, productDatabase: ProductDatabase){
        productDatabase.getProductDao().deleteProduct(product.id)
    }
    fun removeFromCart(cart : Cart, productDatabase: ProductDatabase){
        productDatabase.getProductDao().deleteFromCart(cart.id)
    }
}