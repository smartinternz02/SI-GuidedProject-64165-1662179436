package com.asikalikhan.mindgrocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_name_items")
data class ListNameItems(
    // create itemName variable to
    // store ListNameItems.
    @ColumnInfo(name = "listName")
    var listName: String,

    @ColumnInfo(name = "isListNameChecked")
    var isListNameChecked: Boolean
)
{
    // Primary key is a unique key
    // for different database.
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

