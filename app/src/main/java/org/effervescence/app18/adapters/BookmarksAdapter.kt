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
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.bookmark_event_layout.view.*
import org.effervescence.app18.R
import org.effervescence.app18.activities.EventDetailActivity
import org.effervescence.app18.models.Event
import org.effervescence.app18.utils.AppDB
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by betterclever on 17/09/17.
 */

class BookmarksAdapter(val context: Context) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    private val events = ArrayList<Event>()

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.bookmark_event_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, this, events[position], position)
    }

    fun addEvents(events: List<Event>) {
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun clearData(){
        this.events.clear()
    }

    fun deleteEvent(event: Event, position : Int) {
        this.events.remove(event)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.titleTextView)
        val timeView = itemView.findViewById<TextView>(R.id.timeTextView)
        val locationView = itemView.findViewById<TextView>(R.id.locationTextView)
        fun bindItem(context: Context, adapter: BookmarksAdapter, event: Event, position: Int) {
            titleView.text = event.name

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = event.timestamp.times(1000L)

            val sdf = SimpleDateFormat("hh:mm a  MMMM d, yyyy")
            sdf.timeZone = TimeZone.getTimeZone("Asia/India")

            timeView.text = sdf.format(calendar.time)

            locationView.text = event.location


            if(event.timestamp < 100L){
                locationView.text = "Online"
                timeView.visibility = View.GONE
            }else{
                timeView.visibility = View.VISIBLE
            }
            itemView.setOnClickListener({
                val intent = Intent(context, EventDetailActivity::class.java)
                val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                intent.putExtra("event", event)
                ContextCompat.startActivity(context, intent, optionsCompat.toBundle())
            })

            Glide.with(context)
                    .load(event.imageUrl)
                    .into(itemView.eventImageView)



            itemView.cancelBookmark.setOnClickListener ({
                val appDB = AppDB.getInstance(context)
                adapter.deleteEvent(event, position)
                appDB.removeBookmark(event.id)
                context.toast("Bookmark removed.")
            })
        }
    }
}