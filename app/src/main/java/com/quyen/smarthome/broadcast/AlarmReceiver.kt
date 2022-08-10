package com.quyen.smarthome.broadcast

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.quyen.smarthome.R
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.source.remote.util.APIService
import com.quyen.smarthome.service.TimerService
import com.quyen.smarthome.service.TimerService.Companion.ACTION_ALARM
import com.quyen.smarthome.ui.main.MainActivity
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.Constant.BUNDLE_ALARM
import com.quyen.smarthome.utils.Constant.NOTIFY_CHANNEL_ID
import com.quyen.smarthome.utils.Constant.NOTIFY_ID
import com.quyen.smarthome.utils.Constant.STATE_ON
import dagger.hilt.android.AndroidEntryPoint
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
            device?.let { dv ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                val builder = createNotification(
                    context!!,
                    pendingIntent,
                    dv.device_name,
                    "Thiết bị ${dv.device_name} được ${if (deviceState == STATE_ON) "Bật" else "Tắt"}"
                )
                with(NotificationManagerCompat.from(context)) {
                    notify(NOTIFY_ID, builder.build())
                }

                // send message to MQTT
                val ms = if (deviceState == STATE_ON) "on" else "off"
                val serviceIntent = Intent(context, TimerService::class.java)
                val serviceBundle = Bundle().apply {
                    putString(TimerService.BUNDLE_ACTION_ID, ACTION_ALARM)
                    putString(TimerService.BUNDLE_DEVICE_ID, dv.device_id)
                    putString(TimerService.BUNDLE_DEVICE_MESSAGE, ms)
                }

                serviceIntent.putExtras(serviceBundle)
                context.startService(serviceIntent)
                Timber.d("LNQ>.................... startService(serviceIntent)")
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

}
