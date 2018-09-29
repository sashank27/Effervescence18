package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_developers.*
import org.effervescence.app18.R
import org.effervescence.app18.adapters.DeveloperAdapter
import org.effervescence.app18.utils.AppDB

class DevelopersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_developers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDb = AppDB.getInstance(activity!!)
        val list = appDb.getAllDeveloperMembers()


        val adapter = DeveloperAdapter(activity!!)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        developerRecyclerView.layoutManager = layoutManager
        developerRecyclerView.adapter = adapter

        adapter.swapList(list)
    }

}
