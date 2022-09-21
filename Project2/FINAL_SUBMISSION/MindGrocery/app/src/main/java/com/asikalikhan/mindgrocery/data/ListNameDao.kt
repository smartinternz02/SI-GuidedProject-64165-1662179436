package com.asikalikhan.mindgrocery.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListNameDao {
    // Insert function is used to
    // insert data in database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ListNameItems)

    // Delete function is used to
    // delete data in database.
    @Delete
    suspend fun delete(item: ListNameItems)

    // update function is used to
    // update data in database.
    @Update
    suspend fun update(item: ListNameItems)

    /*
getAllGroceryItems function is used to get
all the data of database.
*/
    @Query("SELECT * FROM list_name_items")
    fun getAllListNameItems(): LiveData<List<ListNameItems>>

    //get total ListNameItems  checked items
    @Query("SELECT *  FROM list_name_items WHERE list_name_items.isListNameChecked == :check ")
    fun getAllCheckedListNameItems(check: Boolean) : LiveData<List<ListNameItems>>

    //Delete all checked items
    @Query("Delete FROM list_name_items WHERE list_name_items.isListNameChecked == :check ")
    fun deleteAllCheckedListName(check: Boolean)

}