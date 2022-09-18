package com.asikalikhan.mindgrocery

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(item: GroceryItems) = db.getGroceryDao().insert(item)
    suspend fun update(item: GroceryItems) = db.getGroceryDao().update(item)
    suspend fun delete(item: GroceryItems) = db.getGroceryDao().delete(item)

    fun allGroceryItems() = db.getGroceryDao().getAllGroceryItems()
    fun allCheckedItemsTotalCost(check :Boolean) = db.getGroceryDao().getAllCheckedItemsTotalCost(check)
    fun allItemsTotalCost() = db.getGroceryDao().getAllItemsTotalCost()

}