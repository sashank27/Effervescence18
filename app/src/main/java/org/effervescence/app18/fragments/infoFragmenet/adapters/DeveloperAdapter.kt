package org.effervescence.app18.fragments.infoFragmenet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.effervescence.app18.R
import org.effervescence.app18.fragments.infoFragmenet.models.Developer

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


    fun swapList(newList : ArrayList<Developer>){
        developerList = newList
        notifyDataSetChanged()
    }
    inner class DeveloperViewHolder(itemView: View, private val itemClick: (Developer) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val DeveloperImageView = itemView.findViewById<ImageView>(R.id.developerImage)
        private val DeveloperNameView = itemView.findViewById<TextView>(R.id.developerNameTextView)
        private val DeveloperDesignationView = itemView.findViewById<TextView>(R.id.developerDesignationTextView)

        fun bind(context : Context, Developer: Developer){
            DeveloperNameView.text = Developer.name
            DeveloperDesignationView.text = Developer.designation
        }
    }
}