package org.effervescence.app18.fragments.infoFragmenet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.effervescence.app18.R
import org.effervescence.app18.fragments.infoFragmenet.models.Person

class PersonAdapter(val context: Context, private val itemClick : (Person) -> Unit) :
        RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(){

    private var personList = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.individual_person_view, parent, false)
        return PersonViewHolder(itemView, itemClick)
    }

    override fun getItemCount(): Int = personList.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(context, personList[position])
    }


    fun swapList(newList : ArrayList<Person>){
        personList = newList
        notifyDataSetChanged()
    }
    inner class PersonViewHolder(itemView: View, private val itemClick: (Person) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val personImageView = itemView.findViewById<ImageView>(R.id.developerImage)
        private val personNameView = itemView.findViewById<TextView>(R.id.developerNameTextView)
        private val personDesignationView = itemView.findViewById<TextView>(R.id.personDesignationTextView)

        fun bind(context : Context, person: Person){
            personNameView.text = person.name
            personDesignationView.text = person.designation
        }
    }
}