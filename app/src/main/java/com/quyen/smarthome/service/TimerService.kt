package com.quyen.smarthome.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import org.eclipse.paho.android.service.MqttAndroidClient
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()
    private var deviceID: String? = ""
    private var message: String? = ""

    @Inject
    lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val bundle = intent.extras
        deviceID = bundle?.getString(BUNDLE_DEVICE_ID)
        message = bundle?.getString(BUNDLE_DEVICE_MESSAGE)
        val time = bundle?.getDouble(BUNDLE_TIME, 0.0)
        timer.scheduleAtFixedRate(time?.let { TimeTask(it) }, 0, 1000)
        return START_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        val intent = Intent(TIMER_UPDATED)
        intent.putExtra(BUNDLE_TIME, 0)
        sendBroadcast(intent)
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time--
            intent.putExtra(BUNDLE_TIME, time)
//            Timber.d("LNQ : time = ${time}")
            sendBroadcast(intent)
            if (time == 0.0) {
                if (!deviceID.isNullOrEmpty() && !message.isNullOrEmpty()) {
                    mqttClientConnect(mqttAndroidClient, {
                        publishMessageMqtt(mqttAndroidClient, deviceID!!, message!!, true)
                    }, { _, _ ->
                        Timber.d("LNQ : MQTT Connection Failed")
                    })
                }
                stopSelf()
            }
        }
    }


    companion object {
        const val TIMER_UPDATED = "timerUpdated"
        const val BUNDLE_TIME = "timeExtra"
        const val BUNDLE_DEVICE_MESSAGE = "BUNDLE_DEVICE_MESSAGE"
        const val BUNDLE_DEVICE_ID = "BUNDLE_DEVICE_ID"
    }

}
