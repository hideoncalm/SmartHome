package com.quyen.smarthome.broadcast

import android.app.PendingIntent
import android.bluetooth.BluetoothClass
import android.content.BroadcastReceiver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewModelScope
import com.quyen.smarthome.R
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.mqttClientConnect
import com.quyen.smarthome.service.publishMessageMqtt
import com.quyen.smarthome.ui.main.MainActivity
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.Constant.BUNDLE_ALARM
import com.quyen.smarthome.utils.Constant.NOTIFY_CHANNEL_ID
import com.quyen.smarthome.utils.Constant.NOTIFY_ID
import com.quyen.smarthome.utils.Constant.STATE_ON
import com.quyen.smarthome.utils.Constant.TURN_OFF_MESSAGE
import com.quyen.smarthome.utils.Constant.TURN_ON_MESSAGE
import com.quyen.smarthome.utils.goAsync
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.eclipse.paho.android.service.MqttAndroidClient
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mqttClient: MqttAndroidClient

    @Inject
    lateinit var espService: APIService

    override fun onReceive(context: Context?, intent: Intent?) {

        val bundle: Bundle? = intent?.getBundleExtra(BUNDLE_ALARM)
        if (bundle != null) {
            val device: Device? = bundle.getParcelable(Constant.DEVICE_KEY)
            val deviceState = bundle.getInt(Constant.DEVICE_STATE_KEY)
            device?.let {  dv ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                val builder = createNotification(
                    context!!,
                    pendingIntent,
                    dv.device_name,
                    "Device ${dv.device_name} is ${if (deviceState == STATE_ON) "ON" else "OFF"}"
                )
                with(NotificationManagerCompat.from(context)) {
                    notify(NOTIFY_ID, builder.build())
                }

                goAsync(GlobalScope, Dispatchers.Default){
                    mqttClientConnect(mqttClient, {
                        if(deviceState == STATE_ON)
                        {
                            turnDeviceOn(dv)
                        }
                        else
                        {
                            turnDeviceOff(dv)
                        }
                    }, { _, _ ->
                        Timber.d("LNQ : Connected Failed")
                    })
                }
            }
        }
    }

    private fun createNotification(
        context: Context,
        pendingIntent: PendingIntent,
        deviceName: String,
        title: String
    ) = NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID)
        .setSmallIcon(R.drawable.smart_home)
        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.smart_home))
        .setContentTitle(deviceName)
        .setContentText(title)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)


    private fun turnDeviceOn(device: Device) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // push message on to topic
                val pushTopic = device.device_id
                publishMessageMqtt(mqttClient, pushTopic, TURN_ON_MESSAGE, true)
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/on"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                }
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }

    private fun turnDeviceOff(device: Device) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // push message off to topic
                val pushTopic = device.device_id
                publishMessageMqtt(
                    mqttClient,
                    pushTopic,
                    TURN_OFF_MESSAGE,
                    true
                )
                // get to local server
                val url: String = "http://" + device.device_ip_addr + "/off"
                val response = espService.turnDeviceOn(url)
                if (response.isSuccessful) {
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

}
