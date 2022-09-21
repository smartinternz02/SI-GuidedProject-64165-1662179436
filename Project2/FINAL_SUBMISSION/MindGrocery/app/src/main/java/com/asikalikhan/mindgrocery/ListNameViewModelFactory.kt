package com.asikalikhan.mindgrocery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asikalikhan.mindgrocery.model.GroceryViewModel

class ListNameViewModelFactory(private val repository: ListNameRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListNameViewModel(repository) as T
    }

}