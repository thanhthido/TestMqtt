package com.thanhthido.testmqtt

data class SensorData(
    val type: String,
    val event: String,
    val msg: String,
    val value: Double
)
