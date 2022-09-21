package com.asikalikhan.mindgrocery.model

import com.asikalikhan.mindgrocery.data.GroceryDatabase
import com.asikalikhan.mindgrocery.data.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(item: GroceryItems) = db.getGroceryDao().insert(item)
    suspend fun update(item: GroceryItems) = db.getGroceryDao().update(item)
    suspend fun delete(item: GroceryItems) = db.getGroceryDao().delete(item)
    //from all lists
    fun allGroceryItems() = db.getGroceryDao().getAllGroceryItems()
    fun allCheckedItemsTotalCost(check :Boolean) = db.getGroceryDao().getAllCheckedItemsTotalCost(check)
    fun allItemsTotalCost() = db.getGroceryDao().getAllItemsTotalCost()

    //particular list
    fun allGroceryItemsRelatedTo(related : String) = db.getGroceryDao().allGroceryItemsRelatedTo(related)
    fun allCheckedItemsTotalCost(check :Boolean,related : String) = db.getGroceryDao().getAllCheckedItemsTotalCost(check,related)
    fun allItemsTotalCost(related: String) = db.getGroceryDao().getAllItemsTotalCost(related)
    fun deleteAllCheckedGroceryItems(check: Boolean,related: String) = db.getGroceryDao().deleteAllCheckedGroceryItems(check,related)

}