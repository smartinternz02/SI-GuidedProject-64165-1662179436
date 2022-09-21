package com.asikalikhan.mindgrocery

import com.asikalikhan.mindgrocery.data.ListNameDatabase
import com.asikalikhan.mindgrocery.data.ListNameItems

class ListNameRepository(private val db : ListNameDatabase) {

    suspend fun insert(items: ListNameItems) = db.getListNameDao().insert(items)
    suspend fun update(items: ListNameItems) = db.getListNameDao().update(items)
    suspend fun delete(items: ListNameItems) = db.getListNameDao().delete(items)

    fun allListNameItems() = db.getListNameDao().getAllListNameItems()
    fun allCheckedListNameItems(check : Boolean) = db.getListNameDao().getAllCheckedListNameItems(check)
    fun deleteCheckedListNameItems(check : Boolean) = db.getListNameDao().deleteAllCheckedListName(check)

}