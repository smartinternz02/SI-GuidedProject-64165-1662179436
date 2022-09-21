package com.asikalikhan.mindgrocery.model

import androidx.lifecycle.ViewModel
import com.asikalikhan.mindgrocery.data.GroceryItems
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

    //particular list
    fun allGroceryItemsRelatedTo(related : String) = repository.allGroceryItemsRelatedTo(related)
    fun allCheckedItemsTotalCost(check :Boolean,related : String) = repository.allCheckedItemsTotalCost(check ,related )
    fun allItemsTotalCost(related: String) = repository.allItemsTotalCost(related)
    fun deleteAllCheckedGroceryItems(check: Boolean,related: String) = repository.deleteAllCheckedGroceryItems(check,related)

}