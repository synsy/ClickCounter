package com.example.clickcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clickcounter.model.CounterItem

@Composable
fun CounterCard(
    counter: CounterItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onIncrementByFive: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = counter.title, style = MaterialTheme.typography.titleMedium)
            Text(text = counter.count.toString(), style = MaterialTheme.typography.headlineSmall)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onDecrement) {
                Text("-1")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onIncrement) {
                Text("+1")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onIncrementByFive) {
                Text("+5")
            }
        }
    }
}
