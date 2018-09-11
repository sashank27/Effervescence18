package org.effervescence.app18.fragments.infoFragmenet


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sponsors.*

import org.effervescence.app18.R
import org.effervescence.app18.fragments.infoFragmenet.adapters.SponsorAdapter
import org.effervescence.app18.fragments.infoFragmenet.models.Sponsor

class SponsorsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<Sponsor>()
        for(i in 0 until 20){
            list.add(Sponsor())
        }

        val adapter = SponsorAdapter(activity!!){
            Toast.makeText(activity, "This is a test message", Toast.LENGTH_SHORT).show()
        }

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        sponsorsRecyclerView.layoutManager = layoutManager
        sponsorsRecyclerView.adapter = adapter

        adapter.swapList(list)
    }

}
