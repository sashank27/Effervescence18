package org.effervescence.app18.adapters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.effervescence.app18.R
import org.effervescence.app18.models.Person

class PersonAdapter(val context: Context, private val itemClick : (Person) -> Unit) :
        RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){

    companion object {
        private const val TYPE_LEADER = 0
        private const val TYPE_PERSON = 1
    }
    private var personList = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = if(viewType == TYPE_PERSON)
            LayoutInflater.from(context).inflate(R.layout.individual_person_view, parent, false)
        else
            LayoutInflater.from(context).inflate(R.layout.indvidual_leader_view, parent, false)

        return PersonViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = personList.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(context, personList[position])
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return TYPE_LEADER
        return TYPE_PERSON
    }

    fun swapList(newList : List<Person>){
        personList.clear()
        personList.addAll(newList)
        notifyDataSetChanged()
    }
    inner class PersonViewHolder(itemView: View, private val itemClick: (Person) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val personImageView = itemView.findViewById<ImageView>(R.id.eventImage)
        private val personNameView = itemView.findViewById<TextView>(R.id.eventNameTextView)
        private val personDesignationView = itemView.findViewById<TextView>(R.id.personDesignationTextView)

        fun bind(context : Context, person: Person){
            personNameView.text = person.name
            personDesignationView.text = person.position

            Glide.with(personImageView).load(person.imageUrl).into(personImageView)

            itemView.setOnClickListener {
                val callNumber = person.contact
                if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            123)
                }
                else
                    context.startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
            }
        }
    }
}
