package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.effervescence.app18.R
import kotlinx.android.synthetic.main.fragment_sponsors.*
import org.effervescence.app18.adapters.SponsorAdapter
import org.effervescence.app18.utils.AppDB

class SponsorsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDb = AppDB.getInstance(activity!!)
        val list = appDb.getAllSponsors()
        list.sortBy { it.priority }

        val adapter = SponsorAdapter(activity!!)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        sponsorsRecyclerView.layoutManager = layoutManager
        sponsorsRecyclerView.adapter = adapter

        adapter.swapList(list)
    }

}
