package com.thanhthido.testmqtt

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MainActivity : AppCompatActivity() {

    private lateinit var mqttClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mqttClient = initMqttClient()

        findViewById<Button>(R.id.btn_connect_server).setOnClickListener {
            connectMqtt()
        }

        findViewById<Button>(R.id.btn_publish).setOnClickListener {
            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "temperature",
                        event = "normal",
                        msg = "Nhiệt độ bình thường",
                        value = 102.0
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "co",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "no2",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "ch4",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "pm25",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "pm1",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )

            publishSensorData(
                msg = Gson().toJson(
                    SensorData(
                        type = "pm10",
                        event = "normal",
                        msg = "Nồng độ bình thường",
                        value = 30.2
                    )
                )
            )
        }
    }

    private fun initMqttClient(): MqttAndroidClient {
        val serverURI = "tcp://driver.cloudmqtt.com:18675"
        return MqttAndroidClient(this, serverURI, "13212312312")
    }

    private fun connectMqtt() {
        val options = MqttConnectOptions()
        options.userName = "burlgbdf"
        options.password = "0--UiYtSUWAZ".toCharArray()

        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Toast.makeText(this@MainActivity, "Connect Mqtt thanh cong", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("ThanhThiDebug", "Connection failure")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    private fun publishSensorData(
        topic: String = "nodeWiFi32/detail",
        msg: String,
        qos: Int = 1,
        retained: Boolean = false
    ) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("ThanhThiDebug", "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("ThanhThiDebug", "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}