package com.asikalikhan.mindgrocery

import androidx.lifecycle.ViewModel
import com.asikalikhan.mindgrocery.data.ListNameItems
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListNameViewModel(private val repository: ListNameRepository) : ViewModel() {

    @OptIn(DelicateCoroutinesApi::class)
    fun insert(item: ListNameItems) = GlobalScope.launch {
        repository.insert(item)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun update(item: ListNameItems) = GlobalScope.launch {
        repository.update(item)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete(item: ListNameItems) = GlobalScope.launch {
        repository.delete(item)
    }
    //Here we initialized allGroceryItems function with repository
    fun allListNameItems() = repository.allListNameItems()
    fun allCheckedListNameItems(check : Boolean) = repository.allCheckedListNameItems(check)
    fun deleteCheckedListNameItems(check: Boolean) = repository.deleteCheckedListNameItems(check)
}