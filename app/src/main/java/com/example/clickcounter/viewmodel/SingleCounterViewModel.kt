package com.example.clickcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.clickcounter.data.CounterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SingleCounterViewModel(private val repository: CounterRepository) : ViewModel() {
    val count: StateFlow<Int> = repository.singleCounterFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun increment() {
        updateCount { it + 1 }
    }

    fun decrement() {
        updateCount { it - 1 }
    }

    fun incrementByFive() {
        updateCount { it + 5 }
    }

    private fun updateCount(transform: (Int) -> Int) {
        viewModelScope.launch {
            val newValue = transform(count.value)
            repository.setSingleCounter(newValue)
        }
    }
}

class SingleCounterViewModelFactory(private val repository: CounterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleCounterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingleCounterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
