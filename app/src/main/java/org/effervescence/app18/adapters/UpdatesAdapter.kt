package org.effervescence.app18.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.effervescence.app18.R
import org.effervescence.app18.activities.EventDetailActivity
import org.effervescence.app18.models.Update
import org.effervescence.app18.utils.AppDB
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdatesAdapter(private val mContext: Context, private val itemClickListener: (Update) -> Unit):
        RecyclerView.Adapter<UpdatesAdapter.UpdateViewHolder>() {

    private var updates= ArrayList<Update>()
    var appDB: AppDB = AppDB.getInstance(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {

        val itemView = LayoutInflater.from(mContext).inflate(R.layout.updates_list_item, parent, false)
        return UpdateViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return updates.size
    }

    override fun onBindViewHolder(viewHolder: UpdateViewHolder, position: Int) {
        viewHolder.bind(mContext, updates[position])
    }

    fun setList(updatedList: ArrayList<Update>) {
        updates = updatedList
        notifyDataSetChanged()
    }

    inner class UpdateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val title = itemView.find<TextView>(R.id.titleTV)
        private val description = itemView.find<TextView>(R.id.descriptionTV)
        private val timestamp = itemView.find<TextView>(R.id.timeTV)

        fun bind(context: Context, update: Update) {
            title.text = update.title
            description.text= update.description

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
            calendar.timeInMillis = update.timestamp

            val sdf = SimpleDateFormat("hh:mm a | dd/MM")

            timestamp.text = sdf.format(calendar.timeInMillis)

            itemView.setOnClickListener {
                val eventDetailsIntent = Intent(context, EventDetailActivity::class.java)
                val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                eventDetailsIntent.putExtra("event", appDB.getEventByID(update.eventID))
                context.startActivity(eventDetailsIntent, optionsCompat.toBundle())
            }
        }
    }
}