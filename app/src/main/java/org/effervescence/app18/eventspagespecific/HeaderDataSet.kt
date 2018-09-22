package org.effervescence.app18.eventspagespecific

interface HeaderDataSet {
    data class ItemData(val gradient: Int,
                        val background: Int,
                        val title: String)

    fun getItemData(pos: Int): ItemData
}
