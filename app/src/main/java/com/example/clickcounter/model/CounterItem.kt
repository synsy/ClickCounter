package com.example.clickcounter.model

import kotlinx.serialization.Serializable

@Serializable
data class CounterItem(
    val id: Long,
    val title: String,
    val count: Int
)
