package com.asikalikhan.mindgrocery.data

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


    //get all items ( particular list)
    @Query("SELECT * FROM grocery_items WHERE grocery_items.isRelatedToList == :related ")
    fun allGroceryItemsRelatedTo(related: String) : LiveData<List<GroceryItems>>

    //get total sum of checked items(particular list)
    @Query("SELECT COALESCE(SUM(COALESCE(itemQuantity*itemPrice,0)),0)  FROM grocery_items WHERE grocery_items.isItemChecked == :check AND grocery_items.isRelatedToList == :related")
    fun getAllCheckedItemsTotalCost(check: Boolean,related: String) : LiveData<Int>

    //(particular list)
    @Query("SELECT SUM(COALESCE(itemQuantity*itemPrice,0))  FROM grocery_items WHERE grocery_items.isRelatedToList == :related")
    fun getAllItemsTotalCost(related: String) : LiveData<Int>

    //Delete all checked items from the current list(particular list)
    @Query("Delete FROM grocery_items WHERE grocery_items.isItemChecked == :check AND grocery_items.isRelatedToList == :related")
    fun deleteAllCheckedGroceryItems(check: Boolean,related: String)

    //for displaying all items from different lists (all lists)
    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItems>>

    //budgeting mode
    //get total sum of all items (all lists)
    @Query("SELECT SUM(COALESCE(itemQuantity*itemPrice,0))  FROM grocery_items")
    fun getAllItemsTotalCost() : LiveData<Int>

    //budgeting mode (all lists)
    @Query("SELECT COALESCE(SUM(COALESCE(itemQuantity*itemPrice,0)),0)  FROM grocery_items WHERE grocery_items.isItemChecked == :check ")
    fun getAllCheckedItemsTotalCost(check: Boolean) : LiveData<Int>

    //budgeting mode (all lists)
    //get total GroceryItems  checked items  (all lists)
    @Query("SELECT *  FROM grocery_items WHERE grocery_items.isItemChecked == :check ")
    fun getAllCheckedGroceryItems(check: Boolean) : LiveData<List<GroceryItems>>


}