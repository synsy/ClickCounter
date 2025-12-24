package com.example.clickcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.clickcounter.data.CounterRepository
import com.example.clickcounter.model.CounterItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MultiCounterViewModel(private val repository: CounterRepository) : ViewModel() {
    private val defaultCounters = listOf(
        CounterItem(id = 1, title = "Counter A", count = 0),
        CounterItem(id = 2, title = "Counter B", count = 0),
        CounterItem(id = 3, title = "Counter C", count = 0)
    )

    val counters: StateFlow<List<CounterItem>> = repository.multiCountersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            val current = repository.multiCountersFlow().first()
            if (current.isEmpty()) {
                repository.setMultiCounters(defaultCounters)
            }
        }
    }

    fun increment(counterId: Long) {
        updateCounter(counterId) { it + 1 }
    }

    fun decrement(counterId: Long) {
        updateCounter(counterId) { it - 1 }
    }

    fun incrementByFive(counterId: Long) {
        updateCounter(counterId) { it + 5 }
    }

    private fun updateCounter(counterId: Long, transform: (Int) -> Int) {
        viewModelScope.launch {
            val updated = counters.value.map { counter ->
                if (counter.id == counterId) {
                    counter.copy(count = transform(counter.count))
                } else {
                    counter
                }
            }
            repository.setMultiCounters(updated)
        }
    }
}

class MultiCounterViewModelFactory(private val repository: CounterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MultiCounterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MultiCounterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
