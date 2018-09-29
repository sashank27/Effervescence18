package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_team.*

import org.effervescence.app18.R
import org.effervescence.app18.adapters.PersonAdapter
import org.effervescence.app18.utils.AppDB


class TeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDb = AppDB.getInstance(activity!!)
        val list = appDb.getAllTeamMembers()

        val adapter = PersonAdapter(activity!!)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        personRecyclerView.layoutManager = layoutManager
        personRecyclerView.adapter = adapter

        adapter.swapList(list)
    }


}
