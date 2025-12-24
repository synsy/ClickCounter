package com.example.clickcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clickcounter.data.CounterRepository
import com.example.clickcounter.ui.MultiCounterScreen
import com.example.clickcounter.ui.SingleCounterScreen
import com.example.clickcounter.viewmodel.MultiCounterViewModelFactory
import com.example.clickcounter.viewmodel.SingleCounterViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickCounterApp()
        }
    }
}

@Composable
fun ClickCounterApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = remember { CounterRepository(context.applicationContext) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ClickCounter") },
                actions = {
                    Row(modifier = Modifier.padding(end = 8.dp)) {
                        TextButton(onClick = { navController.navigate("single") }) {
                            Text("Single")
                        }
                        TextButton(onClick = { navController.navigate("multi") }) {
                            Text("Multi")
                        }
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "single",
            modifier = Modifier.padding(padding)
        ) {
            composable("single") {
                val viewModel = viewModel(
                    factory = SingleCounterViewModelFactory(repository)
                )
                SingleCounterScreen(viewModel)
            }
            composable("multi") {
                val viewModel = viewModel(
                    factory = MultiCounterViewModelFactory(repository)
                )
                MultiCounterScreen(viewModel)
            }
        }
    }
}
