package com.sokoldev.griefresort.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.Book
import com.sokoldev.griefresort.data.models.Item
import com.sokoldev.griefresort.data.repository.BookRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookRepository = BookRepository(application.applicationContext)
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books


    fun loadBooks() {
        val response = bookRepository.getBooksFromAssets()
        response?.bookList.let {
            _books.postValue(it)
            Log.d("BOOOOOK" , it?.size.toString())
        }
    }

}