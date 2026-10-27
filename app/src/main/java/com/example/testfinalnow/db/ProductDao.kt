package com.example.testfinalnow.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProduct() : List<Product>

    @Insert
    fun addProduct(product: Product)

    @Query("DELETE FROM product WHERE id= :id")
    fun deleteProduct(id: Int)

    @Query("SELECT MAX(id) FROM product")
    fun getMaxId() : Int

    @Update
    fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE id= :id")
    fun getProduct(id : Int): Product

    @Insert
    fun addToCart(cart: Cart)

    @Upsert
    fun upsertCartProduct(product: Product)

    @Query("DELETE FROM cart WHERE id= :id")
    fun deleteFromCart(id: Int)

    @Query("SELECT * FROM cart where id= :id")
    fun getSingleFromCart(id: Int): Cart

    @Query("SELECT COUNT(id) FROM cart")
    fun returnCartCount(): Int

    @Query("SELECT * FROM cart")
    fun getAllInCart(): List<Cart>

    @Query("SELECT COUNT(id) FROM cart")
    fun getCountInCart(): Int

    @Query("SELECT SUM(price) FROM cart")
    fun getCartPrice(): Double

    @Insert
    fun addStore(store: Store)

    @Upsert
    fun upsertStore(store: Store)

    @Query("SELECT * FROM store WHERE storeId= :id")
    fun getStore(id: Int): Store

    @Query("DELETE FROM store WHERE storeId= :id")
    fun deleteStore(id: Int)

    @Query("SELECT * FROM store")
    fun getAllStores(): List<Store>

    @Query("SELECT MAX(storeId) FROM store")
    fun getStoreMaxId(): Int

    @Insert
    fun addStoreItems(storeItems: StoreItems)
}
