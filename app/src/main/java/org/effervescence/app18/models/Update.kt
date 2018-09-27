package org.effervescence.app18.models

data class Update(
        var eventID: Long = 0,
        var senderName: String = "",
        var senderEmail: String = "",
        var verified: Boolean = false,
        var title: String = "",
        var description: String = "",
        var timestamp: Long = 0
)