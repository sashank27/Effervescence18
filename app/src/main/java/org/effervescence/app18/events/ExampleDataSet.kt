package org.effervescence.app18.events

import org.effervescence.app18.R

interface HeaderDataSet {
    data class ItemData(val gradient: Int,
                        val background: Int,
                        val title: String)

    fun getItemData(pos: Int): ItemData
}

class ExampleDataSet {
    private val headerBackgrounds = intArrayOf(R.drawable.card_1_background, R.drawable.card_2_background, R.drawable.card_3_background, R.drawable.card_4_background).toTypedArray()
    private val headerGradients = intArrayOf(R.drawable.card_1_gradient, R.drawable.card_2_gradient, R.drawable.card_3_gradient, R.drawable.card_4_gradient).toTypedArray()
    private val headerTitles = arrayOf("Main Stage", "Dramatics", "Music", "Dance", "Fine Arts", "AMS", "Literature", "Gaming", "Informal")




    internal val headerDataSet = object : HeaderDataSet {
        override fun getItemData(pos: Int) =
                HeaderDataSet.ItemData(
                    gradient = headerGradients[pos % headerGradients.size],
                    background = headerBackgrounds[pos % headerBackgrounds.size],
                    title = headerTitles[pos % headerTitles.size])
    }

}