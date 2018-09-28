package org.effervescence.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import org.effervescence.app18.R
import org.effervescence.app18.adapters.BookmarksAdapter
import org.effervescence.app18.adapters.UpcomingAdapter
import org.effervescence.app18.utils.AppDB

class HomeFragment : Fragment() {
    private lateinit var appDB: AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDB = AppDB.getInstance(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.title = "My Effe"

        val layoutManager = LinearLayoutManager(context)
        layoutManager.isAutoMeasureEnabled = true

        bookmarksRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        bookmarksRecyclerView.isNestedScrollingEnabled = false

        bookmarksRecyclerView.setHasFixedSize(true)
        bookmarksRecyclerView.setItemViewCacheSize(20)
        bookmarksRecyclerView.isDrawingCacheEnabled = true
        bookmarksRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(bookmarksRecyclerView)

        upcomingRecyclerView.layoutManager = LinearLayoutManager(context)
        upcomingRecyclerView.isNestedScrollingEnabled = false
        upcomingRecyclerView.setItemViewCacheSize(20)
        upcomingRecyclerView.isDrawingCacheEnabled = true
        upcomingRecyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        appDB = AppDB.getInstance(context!!)
        val upcomingAdapter = UpcomingAdapter(context!!)
        val bookmarksAdapter = BookmarksAdapter(context!!)
        upcomingRecyclerView.adapter = upcomingAdapter
        bookmarksRecyclerView.adapter = bookmarksAdapter

        upcomingAdapter.addEvents(appDB.getAllEvents()
                .filter { ((it.timestamp - 5 * 60 * 60 - 30 * 60) < System.currentTimeMillis() / 1000L)
                        .and(it.timestamp != 2L) }
                .sortedBy { it.timestamp }
                .subList(0, 10))

        if (appDB.getBookmarkedEvents().isEmpty()) {
            bookmarksRecyclerView.visibility = View.GONE
        } else {
            bookmarksRecyclerView.visibility = View.VISIBLE
            appDB.getBookmarkedEvents().let { bookmarksAdapter.addEvents(it.sortedBy { it.timestamp }) }
        }
    }


    override fun onResume() {
        super.onResume()

        if (appDB.getBookmarkedEvents().isEmpty()) {
            bookmarksRecyclerView.visibility = View.GONE
            noDataText.visibility = View.VISIBLE
        } else {
            bookmarksRecyclerView.visibility = View.VISIBLE
            noDataText.visibility = View.GONE
            (bookmarksRecyclerView.adapter as BookmarksAdapter).clearData()
            appDB.getBookmarkedEvents().let {
                (bookmarksRecyclerView.adapter as BookmarksAdapter)
                        .addEvents(it.sortedBy { it.timestamp })
            }
        }

    }
}
