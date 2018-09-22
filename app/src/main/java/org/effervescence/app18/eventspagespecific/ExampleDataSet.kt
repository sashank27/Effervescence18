package org.effervescence.app18.eventspagespecific

import org.effervescence.app18.R


class ExampleDataSet {
    private val headerBackgrounds = intArrayOf(R.drawable.ic_launcher_background).toTypedArray()
    private val headerGradients = intArrayOf(R.drawable.card_1_gradient, R.drawable.card_2_gradient, R.drawable.card_3_gradient, R.drawable.card_4_gradient).toTypedArray()
    private val headerTitles = arrayOf("TECHNOLOGY", "SCIENCE", "MOVIES", "GAMING")


    internal val headerDataSet = object : HeaderDataSet {
        override fun getItemData(pos: Int) =
                HeaderDataSet.ItemData(
                        gradient = headerGradients[pos % headerGradients.size],
                        background = headerBackgrounds[pos % headerBackgrounds.size],
                        title = headerTitles[pos % headerTitles.size])
    }


}