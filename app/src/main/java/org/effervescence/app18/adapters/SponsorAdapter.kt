package org.effervescence.app18.adapters

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.effervescence.app18.R
import org.effervescence.app18.models.Sponsor

class SponsorAdapter(val context: Context, private val itemClick : (Sponsor) -> Unit) :
        RecyclerView.Adapter<SponsorAdapter.SponsorViewHolder>(){

    private var developerList = ArrayList<Sponsor>()
    val requestOption = RequestOptions().placeholder(R.drawable.placeholder_event)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.individual_sponsor_view, parent, false)
        return SponsorViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = developerList.size

    override fun onBindViewHolder(holder: SponsorViewHolder, position: Int) {
        holder.bind(context, developerList[position])
    }


    fun swapList(newList :List<Sponsor>){
        developerList.clear()
        developerList.addAll(newList)
        notifyDataSetChanged()
    }
    inner class SponsorViewHolder(itemView: View, private val itemClick: (Sponsor) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val sponsorImageView = itemView.findViewById<ImageView>(R.id.sponsorImageView)
        private val sponsorNameView = itemView.findViewById<TextView>(R.id.sponsorNameTextView)
        private val sponsorTypeView = itemView.findViewById<TextView>(R.id.sponsorTypeTextView)

        fun bind(context : Context, sponsor: Sponsor) {
            sponsorNameView.text = sponsor.name
            sponsorTypeView.text = sponsor.categories.joinToString(", ")
            Glide.with(sponsorImageView).load(sponsor.imageUrl).apply(requestOption).into(sponsorImageView)

            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()

                if(sponsor.website.isNotEmpty()) {
                    try {
                        customTabsIntent.launchUrl(context, Uri.parse(sponsor.website))
                    } catch (e: Exception) {
                        Toast.makeText(context, "Unable to open web page", Toast.LENGTH_SHORT).show()
                    }
                }

                if(sponsor.website.isNotEmpty())
                    customTabsIntent.launchUrl(context, Uri.parse(sponsor.website))
            }
        }
    }
}