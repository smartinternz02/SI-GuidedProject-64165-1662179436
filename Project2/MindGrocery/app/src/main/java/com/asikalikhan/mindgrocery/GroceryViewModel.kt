package com.asikalikhan.mindgrocery

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {

    // In coroutines thread insert item in insert function.

    @OptIn(DelicateCoroutinesApi::class)
    fun insert(item: GroceryItems) = GlobalScope.launch {
        repository.insert(item)
    }

    // In coroutines thread update item in delete function.


    @OptIn(DelicateCoroutinesApi::class)
    fun update(item: GroceryItems) = GlobalScope.launch {
        repository.update(item)
    }

    // In coroutines thread delete item in delete function.

    @OptIn(DelicateCoroutinesApi::class)
    fun delete(item: GroceryItems) = GlobalScope.launch {
        repository.delete(item)
    }

    //Here we initialized allGroceryItems function with repository
    fun allGroceryItems() = repository.allGroceryItems()
    fun allCheckedItemsTotalCost(check : Boolean) = repository.allCheckedItemsTotalCost(check)
    fun allItemsTotalCost() = repository.allItemsTotalCost()

}