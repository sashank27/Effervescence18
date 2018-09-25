package org.effervescence.app18.events.header

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ramotion.navigationtoolbar.HeaderLayout
import org.effervescence.app18.R
import org.jetbrains.anko.find
import org.w3c.dom.Text

class HeaderItem(view: View) : HeaderLayout.ViewHolder(view) {
    private val gradient = view.findViewById<View>(R.id.gradient)
    private val background = view.findViewById<ImageView>(R.id.image)
    private val headerTitle = view.findViewById<TextView>(R.id.heading)

    internal val backgroundLayout = view.findViewById<View>(R.id.backgroud_layout)

    private val headerBackgrounds = intArrayOf(R.drawable.card_1_background, R.drawable.card_2_background, R.drawable.card_3_background, R.drawable.card_4_background).toTypedArray()
    private val headerTitles = arrayOf("Main Stage", "Dramatics", "Music", "Dance", "Fine Arts", "AMS", "Literature", "Gaming", "Informal")
    private val headerGradients = intArrayOf(R.drawable.card_1_gradient, R.drawable.card_2_gradient, R.drawable.card_3_gradient, R.drawable.card_4_gradient).toTypedArray()



    internal var overlayTitle: TextView? = null
    internal var overlayLine: View? = null


    fun setContent(position: Int, title: TextView?, line: View?) {
        gradient.setBackgroundResource(headerGradients[position%4])
        Glide.with(background).load(headerBackgrounds[position%4]).into(background)

        overlayTitle = title?.also {
            it.tag = position
            it.text = ""
            it.visibility = View.VISIBLE
        }
        headerTitle.text = headerTitles[position]

        overlayLine = line
        overlayLine?.also {
            it.tag = position
            it.visibility = View.VISIBLE
        }
    }



    fun clearContent() {
        overlayTitle?.also {
            it.visibility = View.GONE
            it.tag = null
        }

        overlayLine?.also {
            it.tag = null
            it.visibility = View.GONE
        }

        overlayTitle = null
        overlayLine = null
    }

}