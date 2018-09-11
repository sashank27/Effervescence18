package org.effervescence.app18.fragments.infoFragmenet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.effervescence.app18.R
import org.effervescence.app18.fragments.infoFragmenet.models.Sponsor

class SponsorAdapter(val context: Context, private val itemClick : (Sponsor) -> Unit) :
        RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder>(){

    private var developerList = ArrayList<Sponsor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.individual_sponsor_view, parent, false)
        return SponsorViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = developerList.size

    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        holder.bind(context, developerList[position])
    }


    fun swapList(newList : ArrayList<Sponsor>){
        developerList = newList
        notifyDataSetChanged()
    }
    inner class SponsorViewHolder(itemView: View, private val itemClick: (Sponsor) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val sponsorImageView = itemView.findViewById<ImageView>(R.id.sponsorImageView)
        private val sponsorNameView = itemView.findViewById<TextView>(R.id.sponsorNameTextView)
        private val sponsorTypeView = itemView.findViewById<TextView>(R.id.sponsorTypeTextView)

        fun bind(context : Context, sponsor: Sponsor){
            sponsorNameView.text = sponsor.name
            sponsorTypeView.text = sponsor.sponshorshipType
        }
    }
}