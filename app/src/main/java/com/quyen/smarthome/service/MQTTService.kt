package com.quyen.smarthome.service

import android.content.Context
import com.quyen.smarthome.utils.Constant.MQTT_SERVER_URL
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.UnsupportedEncodingException


fun setupAndroidMqttClient(applicationContext: Context) {
    val clientId: String = MqttClient.generateClientId();
    val client: MqttAndroidClient =
        MqttAndroidClient(applicationContext, MQTT_SERVER_URL, clientId)
    try {
        val token: IMqttToken = client.connect()
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            }
        };
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun publishMessageMqtt(client: MqttAndroidClient, topic: String, payload: String) {
    var encodedPayload = ByteArray(0)
    try {
        encodedPayload = payload.toByteArray(charset("UTF-8"))
        val message = MqttMessage(encodedPayload)
        client.publish(topic, message)
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun publishMessageRetainedMqtt(client: MqttAndroidClient, topic: String, payload: String) {
    var encodedPayload = ByteArray(0)
    try {
        encodedPayload = payload.toByteArray(charset("UTF-8"))
        val message = MqttMessage(encodedPayload)
        message.isRetained = true
        client.publish(topic, message)
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun subscribeMqtt(client: MqttAndroidClient, topic: String) {
    val qos = 1
    try {
        val subToken: IMqttToken = client.subscribe(topic, qos)
        subToken.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                // The message was published
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                // The subscription could not be performed, maybe the user was not
                // authorized to subscribe on the specified topic e.g. using wildcards
            }
        }
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
                // we are now successfully disconnected
            }

            override fun onFailure(
                asyncActionToken: IMqttToken,
                exception: Throwable
            ) {
                // something went wrong, but probably we are disconnected anyway
            }
        }
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}
