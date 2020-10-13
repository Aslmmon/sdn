package com.softwareDrivingNetwork.sdn.models.general.notification

data class NotificationModel(
    val accountid: String,
    val last_notify_time: Any,
    val max_per_day: Int,
    val min_events_activation: Int,
    val notification_desc: String,
    val notificationid: String,
    val notify_emails: String,
    val notify_sms: String,
    val only_preferred_time: Boolean,
    val preferred_endtime: String,
    val preferred_starttime: String,
    val userid: String
)