package com.example.teashop.screen.screen.category_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.category.ParentCategory
import com.example.teashop.data.repository.CategoryRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _categoryList = MutableLiveData<List<Category>>()

    val categoryList: LiveData<List<Category>>
        get() = _categoryList

    fun loadCategories(parentCategory: ParentCategory) {
        viewModelScope.launch(exceptionHandler) {
            val categories = CategoryRepository().getCategories(parentCategory)
            _categoryList.value = categories
        }
    }
}