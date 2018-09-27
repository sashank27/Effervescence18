package org.effervescence.app18.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.upcoming_event_layout.view.*
import org.effervescence.app18.R
import org.effervescence.app18.activities.EventDetailActivity
import org.effervescence.app18.models.Event
import java.text.SimpleDateFormat
import java.util.*

class UpcomingAdapter(val context: Context) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {

    private val events = ArrayList<Event>()

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.upcoming_event_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (position == 0 || position == 1) {
//            holder.rootLayout.visibility = View.INVISIBLE
//        } else {
//            holder.rootLayout.visibility = View.VISIBLE
            holder.bindItem(context, events[position])
//        }
    }

    fun addEvents(events: List<Event>) {
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        val rootLayout = itemView.findViewById<LinearLayout>(R.id.rootLayout)

        fun bindItem(context: Context, event: Event) {
            itemView.eventNameTV.text = event.name

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("MMM dd, hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            itemView.eventTimeTV.text = sdf.format(calendar.time)

            if(event.timestamp < 100L){
                itemView.eventTimeTV.text = ""
            }else{
                itemView.eventTimeTV.visibility = View.VISIBLE
            }
            Glide.with(context)
                    .load(event.imageUrl)
                    .into(itemView.eventImageView)

            itemView.rootLayout.setOnClickListener {
                val intent = Intent(context, EventDetailActivity::class.java)
                val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                intent.putExtra("event", event)
                ContextCompat.startActivity(context, intent, optionsCompat.toBundle())
            }
        }
    }
}