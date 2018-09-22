package org.effervescence.app18.events.pager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.effervescence.app18.R
import org.effervescence.app18.models.Event
import java.util.*


class ViewPagerAdapter(private val mContext: Context, private val count: Int,
                       private val mData: Map<String, List<Event>>) : PagerAdapter() {

    private companion object {
        private val headerTitles = arrayOf("Main Stage", "Dramatics", "Music", "Dance", "Fine Arts", "AMS", "Literature", "Gaming" , "Informal")
    }

    override fun getCount(): Int = count

    override fun isViewFromObject(view: View, key: Any): Boolean = view == key

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.pager_item, container, false)
        initRecyclerView(view as RecyclerView, position)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, key: Any) {
        container.removeView(key as View)
    }

    override fun getPageTitle(position: Int): CharSequence = position.toString()

    private fun initRecyclerView(recyclerView: RecyclerView, position: Int) {
        val adapter = EventAdapter(mContext){

        }
        adapter.swapList(mData[headerTitles[position]]!!.toList())
        recyclerView.adapter = adapter
    }

}