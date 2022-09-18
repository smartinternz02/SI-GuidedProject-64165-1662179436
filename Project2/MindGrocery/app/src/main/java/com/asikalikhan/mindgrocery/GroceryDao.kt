package com.asikalikhan.mindgrocery

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDao {

    // Insert function is used to
    // insert data in database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    // Delete function is used to
    // delete data in database.
    @Delete
    suspend fun delete(item: GroceryItems)

    // update function is used to
    // update data in database.
    @Update
    suspend fun update(item: GroceryItems)

    /*
getAllGroceryItems function is used to get
all the data of database.
*/
    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItems>>

    //get total sum of checked items
    @Query("SELECT COALESCE(SUM(COALESCE(itemQuantity*itemPrice,0)),0)  FROM grocery_items WHERE grocery_items.isItemChecked == :check ")
    fun getAllCheckedItemsTotalCost(check: Boolean) : LiveData<Int>

    //get total sum of all items
    @Query("SELECT SUM(COALESCE(itemQuantity*itemPrice,0))  FROM grocery_items")
    fun getAllItemsTotalCost() : LiveData<Int>
}