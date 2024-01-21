package com.ivanshalimov.practicecoroutinecoursesais.network


data class Root(
    val activity: String,
    val type: String,
    val participants: Long,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double,
)