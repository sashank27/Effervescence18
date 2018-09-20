package org.effervescence.app18.events.pager

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.effervescence.app18.R
import org.effervescence.app18.models.Event

class EventAdapter(val context: Context, private val itemClick : (Event) -> Unit) :
        RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    private var EventList = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false)
        return EventViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = EventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(context, EventList[position])
    }


    fun swapList(newList : List<Event>){
        EventList.clear()
        EventList.addAll(newList)
        notifyDataSetChanged()
    }
    inner class EventViewHolder(itemView: View, private val itemClick: (Event) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val EventImageView = itemView.findViewById<ImageView>(R.id.eventImage)
        private val EventNameView = itemView.findViewById<TextView>(R.id.eventNameTextView)
        private val EventTimeView = itemView.findViewById<TextView>(R.id.eventTimeTextView)
        private val EventDayView = itemView.findViewById<TextView>(R.id.eventDayTextView)
        private val EventLocationTextView = itemView.findViewById<TextView>(R.id.eventLocation)

        fun bind(context : Context, Event: Event){
            EventNameView.text = Event.name
            EventDayView.text = Event.timestamp.toString()
            EventLocationTextView.text = Event.location
        }
    }
}