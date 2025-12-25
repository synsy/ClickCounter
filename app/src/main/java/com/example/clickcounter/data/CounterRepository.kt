package com.example.clickcounter.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.clickcounter.model.CounterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "counter_prefs")

class CounterRepository(private val context: Context) {
    private val singleCounterKey = intPreferencesKey("single_counter")
    private val multiCountersKey = stringPreferencesKey("multi_counters")
    private val json = Json { ignoreUnknownKeys = true }

    fun singleCounterFlow(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[singleCounterKey] ?: 0
        }
    }

    suspend fun setSingleCounter(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[singleCounterKey] = value
        }
    }

    fun multiCountersFlow(): Flow<List<CounterItem>> {
        return context.dataStore.data.map { preferences ->
            val raw = preferences[multiCountersKey]
            if (raw.isNullOrBlank()) {
                emptyList()
            } else {
                runCatching {
                    json.decodeFromString(ListSerializer(CounterItem.serializer()), raw)
                }.getOrDefault(emptyList())
            }
        }
    }

    suspend fun setMultiCounters(counters: List<CounterItem>) {
        val encoded = json.encodeToString(ListSerializer(CounterItem.serializer()), counters)
        context.dataStore.edit { preferences ->
            preferences[multiCountersKey] = encoded
        }
    }
}
