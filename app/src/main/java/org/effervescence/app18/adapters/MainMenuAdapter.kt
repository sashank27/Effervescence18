package org.effervescence.app18.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView

class MainMenuAdapter(private val mOptions: ArrayList<String>): BaseAdapter() {

    private val mOptionViews = ArrayList<DuoOptionView>()

    fun setSelectedView(position: Int) {

        for (i in mOptionViews.indices) {
            mOptionViews[i].isSelected = i == position
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val optionView: DuoOptionView = if (convertView == null) {
            DuoOptionView(parent!!.context)
        } else {
            convertView as DuoOptionView
        }

        optionView.bind(mOptions[position])
        mOptionViews.add(optionView)

        return optionView
    }

    override fun getItem(position: Int): Any {
        return mOptions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mOptions.size
    }
}