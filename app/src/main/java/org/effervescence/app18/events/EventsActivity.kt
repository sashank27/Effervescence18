package org.effervescence.app18.events

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.widget.FrameLayout
import com.ramotion.navigationtoolbar.HeaderLayout
import com.ramotion.navigationtoolbar.HeaderLayoutManager
import com.ramotion.navigationtoolbar.NavigationToolBarLayout
import com.ramotion.navigationtoolbar.SimpleSnapHelper
import org.effervescence.app18.R
import org.effervescence.app18.R.id.navigation_toolbar_layout
import org.effervescence.app18.events.header.HeaderAdapter
import org.effervescence.app18.events.header.HeaderItemTransformer
import org.effervescence.app18.events.pager.ViewPagerAdapter
import org.effervescence.app18.models.Event
import org.effervescence.app18.utils.AppDB
import kotlin.math.ceil
import kotlin.math.max

class EventsActivity : AppCompatActivity() {

    private val itemCount = 9
    private val dataSet = ExampleDataSet()

    private var isExpanded = true
    private var prevAnchorPosition = 0
    val eventData = HashMap<String, List<Event>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val appDB = AppDB.getInstance(this)


        eventData["Main Stage"] = appDB.getEventsOfCategory("main stage")
        eventData["Dramatics"] = appDB.getEventsOfCategory("dramatics")
        eventData["Music"] = appDB.getEventsOfCategory("music")
        eventData["Dance"] = appDB.getEventsOfCategory("dance")
        eventData["Fine Arts"] = appDB.getEventsOfCategory("fine arts")
        eventData["AMS"] = appDB.getEventsOfCategory("AMS")
        eventData["Literature"] = appDB.getEventsOfCategory("literature")
        eventData["Gaming"] = appDB.getEventsOfCategory("gaming")
        eventData["Informal"] = appDB.getEventsOfCategory("informal")

        val header = findViewById<NavigationToolBarLayout>(R.id.navigation_toolbar_layout)
        val viewPager = findViewById<ViewPager>(R.id.pager)
        initActionBar()
        initViewPager(header, viewPager)
        initHeader(header, viewPager)
    }



    private fun initActionBar() {
        val toolbar = findViewById<NavigationToolBarLayout>(R.id.navigation_toolbar_layout).toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initViewPager(header: NavigationToolBarLayout, viewPager: ViewPager) {
        viewPager.adapter = ViewPagerAdapter(this, itemCount, eventData)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                header.smoothScrollToPosition(position)
            }
        })
    }

    private fun initHeader(header: NavigationToolBarLayout, viewPager: ViewPager) {
        val titleLeftOffset = resources.getDimensionPixelSize(R.dimen.title_left_offset)
        val lineRightOffset = resources.getDimensionPixelSize(R.dimen.line_right_offset)
        val lineBottomOffset = resources.getDimensionPixelSize(R.dimen.line_bottom_offset)
        val lineTitleOffset = resources.getDimensionPixelSize(R.dimen.line_title_offset)

        val headerOverlay = findViewById<FrameLayout>(R.id.header_overlay)
        header.setItemTransformer(HeaderItemTransformer(headerOverlay,
                titleLeftOffset, lineRightOffset, lineBottomOffset, lineTitleOffset))
        header.setAdapter(HeaderAdapter(itemCount, dataSet.headerDataSet, headerOverlay))

        header.addItemChangeListener(object : HeaderLayoutManager.ItemChangeListener {
            override fun onItemChangeStarted(position: Int) {
                prevAnchorPosition = position
            }

            override fun onItemChanged(position: Int) {
                viewPager.currentItem = position
            }
        })

        header.addItemClickListener(object : HeaderLayoutManager.ItemClickListener {
            override fun onItemClicked(viewHolder: HeaderLayout.ViewHolder) {
                viewPager.currentItem = viewHolder.position
            }
        })

        SimpleSnapHelper().attach(header)
        initDrawerArrow(header)
        initHeaderDecorator(header)
    }

    private fun initDrawerArrow(header: NavigationToolBarLayout) {
        val drawerArrow = DrawerArrowDrawable(this)
        drawerArrow.color = ContextCompat.getColor(this, android.R.color.white)
        drawerArrow.progress = 1f

        header.addHeaderChangeStateListener(object : HeaderLayoutManager.HeaderChangeStateListener() {
            private fun changeIcon(progress: Float) {
                ObjectAnimator.ofFloat(drawerArrow, "progress", progress).start()
                isExpanded = progress == 1f
                if (isExpanded) {
                    prevAnchorPosition = header.getAnchorPos()
                }
            }

            override fun onMiddle() = changeIcon(0f)
            override fun onExpanded() = changeIcon(1f)
        })

        val toolbar = header.toolBar
        toolbar.navigationIcon = drawerArrow
        toolbar.setNavigationOnClickListener {
            if (!isExpanded) {
                return@setNavigationOnClickListener
            }
            val anchorPos = header.getAnchorPos()
            if (anchorPos == HeaderLayout.INVALID_POSITION) {
                return@setNavigationOnClickListener
            }

            if (anchorPos == prevAnchorPosition) {
                header.collapse()
            } else {
                header.smoothScrollToPosition(prevAnchorPosition)
            }
        }
    }

    private fun initHeaderDecorator(header: NavigationToolBarLayout) {
        val decorator = object :
                HeaderLayoutManager.ItemDecoration,
                HeaderLayoutManager.HeaderChangeListener {

            private val dp5 = resources.getDimensionPixelSize(R.dimen.decor_bottom)

            private var bottomOffset = dp5

            override fun onHeaderChanged(lm: HeaderLayoutManager, header: HeaderLayout, headerBottom: Int) {
                val ratio = max(0f, headerBottom.toFloat() / header.height - 0.5f) / 0.5f
                bottomOffset = ceil(dp5 * ratio).toInt()
            }

            override fun getItemOffsets(outRect: Rect, viewHolder: HeaderLayout.ViewHolder) {
                outRect.bottom = bottomOffset
            }
        }

        header.addItemDecoration(decorator)
        header.addHeaderChangeListener(decorator)
    }
}


