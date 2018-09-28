package org.effervescence.app18.events.pager

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.effervescence.app18.R
import org.effervescence.app18.activities.EventDetailActivity
import org.effervescence.app18.models.Event
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(val context: Context, private val itemClick: (Event) -> Unit) :
        RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var EventList = ArrayList<Event>()

    val requestOption = RequestOptions().placeholder(R.drawable.placeholder_event)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false)
        return EventViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = EventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(context, EventList[position])
    }


    fun swapList(newList: List<Event>) {
        EventList.clear()
        EventList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View, private val itemClick: (Event) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val EventImageView = itemView.findViewById<ImageView>(R.id.eventImage)
        private val EventNameView = itemView.findViewById<TextView>(R.id.eventNameTextView)
        private val EventTimeView = itemView.findViewById<TextView>(R.id.eventTimeTextView)
        private val EventDayView = itemView.findViewById<TextView>(R.id.eventDayTextView)
        private val EventLocationTextView = itemView.findViewById<TextView>(R.id.eventLocation)

        fun bind(context: Context, event: Event) {
            EventNameView.text = event.name
            EventLocationTextView.text = event.location

            if (event.timestamp < 100L) {
                EventLocationTextView.text = "Online"
                EventTimeView.visibility = View.INVISIBLE
                EventDayView.visibility = View.INVISIBLE
            } else {
                EventTimeView.visibility = View.VISIBLE
                EventDayView.visibility = View.VISIBLE
            }
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            EventTimeView.text = sdf.format(calendar.time)

            sdf.applyPattern("d MMM")
            EventDayView.text = sdf.format(calendar.time)

            Glide.with(context).load(event.imageUrl).apply(requestOption).into(EventImageView)

            itemView.setOnClickListener {
                val intent = Intent(context, EventDetailActivity::class.java)
                val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                intent.putExtra("event", event)
                startActivity(context, intent, optionsCompat.toBundle())
            }
        }
    }
}