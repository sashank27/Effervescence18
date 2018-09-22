package org.effervescence.app18.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.effervescence.app18.R
import org.effervescence.app18.models.Developer

class DeveloperAdapter(val context: Context, private val itemClick : (Developer) -> Unit) :
        RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>(){

    private var developerList = ArrayList<Developer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.individual_developer_view, parent, false)
        return DeveloperViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = developerList.size

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        holder.bind(context, developerList[position])
    }


    fun swapList(newList : List<Developer>){
        developerList.clear()
        developerList.addAll(newList)
        notifyDataSetChanged()
    }
    inner class DeveloperViewHolder(itemView: View, private val itemClick: (Developer) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val DeveloperImageView = itemView.findViewById<ImageView>(R.id.eventImage)
        private val DeveloperNameView = itemView.findViewById<TextView>(R.id.eventNameTextView)
        private val DeveloperDesignationView = itemView.findViewById<TextView>(R.id.developerDesignationTextView)

        fun bind(context : Context, Developer: Developer){
            DeveloperNameView.text = Developer.name
            DeveloperDesignationView.text = Developer.position

            Glide.with(DeveloperImageView).load(Developer.imageUrl).into(DeveloperImageView)

            itemView.setOnClickListener {
                openChromeTab(context,Developer.gitHubLink)
            }
        }
        fun openChromeTab(context: Context,url: String){
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

    }
}