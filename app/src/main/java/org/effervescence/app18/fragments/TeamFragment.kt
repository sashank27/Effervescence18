package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_team.*

import org.effervescence.app18.R
import org.effervescence.app18.adapters.PersonAdapter
import org.effervescence.app18.models.Person
import org.effervescence.app18.utils.AppDB


class TeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDb = AppDB.getInstance(activity!!)
        val list = appDb.getAllTeamMembers()

        val adapter = PersonAdapter(activity!!) {
            Toast.makeText(activity, "This is a test message", Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        personRecyclerView.layoutManager = layoutManager
        personRecyclerView.adapter = adapter

        adapter.swapList(list)
    }


}
