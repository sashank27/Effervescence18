package org.effervescence.app18.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

    fun swapList(newList : ArrayList<Person>){
        personList = newList
        notifyDataSetChanged()
    }
    inner class PersonViewHolder(itemView: View, private val itemClick: (Person) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val personImageView = itemView.findViewById<ImageView>(R.id.eventImage)
        private val personNameView = itemView.findViewById<TextView>(R.id.eventNameTextView)
        private val personDesignationView = itemView.findViewById<TextView>(R.id.personDesignationTextView)

        fun bind(context : Context, person: Person){
            personNameView.text = person.name
            personDesignationView.text = person.position
        }
    }
}