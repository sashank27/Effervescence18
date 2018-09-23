package org.effervescence.app18.models

data class Update(
        var id: String = "",
        var eventID: Long = 0,
        var title: String = "",
        var description: String = "",
        var timestamp: Long = 0
)