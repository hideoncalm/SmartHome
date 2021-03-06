package com.quyen.smarthome.utils

object Constant {
    const val BASE_URL = "google.com"

    const val USER_PATH = "user"
    const val HOME_PATH = "home"
    const val ROOM_PATH = "room"
    const val DEVICE_PATH = "device"
    const val RELAY_PATH = "relay"
    const val DHT11_PATH = "DHT11"

    const val STATUS_ON = 1
    const val STATUS_OFF = 0
    const val NOT_REPEAT = -2
    const val REPEAT = -1
    const val SUN = 1
    const val MONDAY = 2
    const val TUE = 3
    const val WED = 4
    const val THUS = 5
    const val FRI = 6
    const val SAT = 7

    const val MQTT_SERVER_URL = "tcp://broker.hivemq.com:1883"

    const val DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss"
    const val STATE_ON = 1
    const val STATE_OFF = 0


    const val NOTIFY_CHANNEL_ID = "1998"
    const val NOTIFY_ID = 1998
    const val NOTIFY_CHANNEL_NAME = "LNQ Notification"
    const val DEVICE_KEY = "device"
    const val ROOM_KEY = "room"
    const val DEVICE_STATE_KEY = "DEVICE_STATE_KEY"
    const val SOURCE_DIRECTION = "srcDirection"
    const val SCENE_KEY = "scene"
    const val HOME_KEY = "home"
    const val BUNDLE_ALARM = "BUNDLE_ALARM"

    const val KEY_DIALOG_COUNTER_TO_DEVICE_DETAIL = "KEY_DIALOG_COUNTER_TO_DEVICE_DETAIL"

    const val TURN_ON_MESSAGE = "ON"
    const val TURN_OFF_MESSAGE = "OFF"
    const val DEVICE_INFO = "/device_info"
    const val ROOM_ALL_DEVICE = "/all_devices_state"
    const val TOPIC_SPLIT = "/"
    const val QOS = 1
    const val ON = "on"
    const val OFF = "off"

    const val SHARED_USER_KEY = "SHARED_USER_KEY"
    const val SHARED_PASSWORD_KEY = "SHARED_PASSWORD_KEY"

}
