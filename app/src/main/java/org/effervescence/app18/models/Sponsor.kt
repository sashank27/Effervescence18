package org.effervescence.app18.models

data class Sponsor(
        var id: Long = 0,
        var name: String = "",
        var imageUrl: String = "",
        var categories: List<String> = emptyList(),
        var website: String = "",
        var priority: Int = -1
)