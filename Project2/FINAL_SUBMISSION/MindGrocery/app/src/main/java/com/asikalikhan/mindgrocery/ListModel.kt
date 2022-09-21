package com.asikalikhan.mindgrocery

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class ListModel() : ViewModel() {
    private lateinit var listName: String
    fun setRelatedString(Name : String) {
        listName = Name
    }

    fun getRelatedString() : String = listName

    private var editMode by Delegates.notNull<Boolean>()

    fun setBudgetMode(edit : Boolean) {
        editMode = edit
    }

    fun getBudgetMode() : Boolean = editMode

}