package com.example.clickcounter.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clickcounter.ui.components.CounterCard
import com.example.clickcounter.viewmodel.MultiCounterViewModel

@Composable
fun MultiCounterScreen(viewModel: MultiCounterViewModel) {
    val counters by viewModel.counters.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Text(
                text = "Your Counters",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        items(counters, key = { it.id }) { counter ->
            CounterCard(
                counter = counter,
                onIncrement = { viewModel.increment(counter.id) },
                onDecrement = { viewModel.decrement(counter.id) },
                onIncrementByFive = { viewModel.incrementByFive(counter.id) }
            )
        }
    }
}
