package com.quyen.smarthome.service

import android.content.Context
import android.os.MessageQueue
import com.quyen.smarthome.utils.Constant.MQTT_SERVER_URL
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber
import java.io.UnsupportedEncodingException


fun setupAndroidMqttClient(applicationContext: Context) {
    val clientId: String = MqttClient.generateClientId()
    val client: MqttAndroidClient =
        MqttAndroidClient(applicationContext, MQTT_SERVER_URL, clientId)
    try {
        val token: IMqttToken = client.connect()
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Timber.d("MQTT client : Connected Success")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Timber.d("MQTT client : Connected Failed")
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun mqttClientConnect(
    client: MqttAndroidClient,
    onConnectedSuccess: (asyncActionToken: IMqttToken?) -> Unit,
    onConnectionFailed: (asyncActionToken: IMqttToken?, exception: Throwable?) -> Unit
) {
    try {
        val token: IMqttToken = client.connect()
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                onConnectedSuccess(asyncActionToken)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                onConnectionFailed(asyncActionToken, exception)
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun publishMessageMqtt(
    client: MqttAndroidClient,
    topic: String,
    payload: String,
    isRetain: Boolean
) {
    var encodedPayload = ByteArray(0)
    try {
        encodedPayload = payload.toByteArray(charset("UTF-8"))
        val message = MqttMessage(encodedPayload)
        message.isRetained = isRetain
        client.publish(topic, message)
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun subscribeMqtt(
    client: MqttAndroidClient,
    topic: String,
    qos: Int = 1,
    onMessageReceive: (topic: String?, message: MqttMessage?) -> Unit
) {
    try {
        val subToken: IMqttToken = client.subscribe(topic, qos)
        client.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                onMessageReceive(topic, message)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }

        })

    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun unsubscribeMqtt(client: MqttAndroidClient, topic: String) {
    try {
        val unsubToken: IMqttToken = client.unsubscribe(topic)
        unsubToken.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                // The subscription could successfully be removed from the client
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                // some error occurred, this is very unlikely as even if the client
                // did not had a subscription to the topic the unsubscribe action
                // will be successfully
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun disconnectMqtt(client: MqttAndroidClient) {
    try {
        val disconToken: IMqttToken = client.disconnect()
        disconToken.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Timber.d("Disconnected Success")
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}
