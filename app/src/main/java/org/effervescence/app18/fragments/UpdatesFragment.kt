package org.effervescence.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_updates.*
import okhttp3.OkHttpClient
import okhttp3.Request

import org.effervescence.app18.R
import org.effervescence.app18.adapters.UpdatesAdapter
import org.effervescence.app18.models.Update
import org.effervescence.app18.utils.AppDB
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class UpdatesFragment : Fragment() {

    private lateinit var adapter: UpdatesAdapter
    private var updatesList = ArrayList<Update>()
    private lateinit var appDB: AppDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updates_RV_swipe.setOnRefreshListener { updateRecyclerView() }
        appDB = AppDB.getInstance(context!!)
        adapter = UpdatesAdapter(context!!)
        buildRecyclerView()
    }

    private fun updateRecyclerView() {
        doAsync {
            updatesList = getAllUpdates()
            uiThread {
                adapter.setList(updatesList)
                updates_RV_swipe.isRefreshing = false
            }
        }
    }

    private fun buildRecyclerView() {
        updates_RV_swipe.isRefreshing = true
        updates_RV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        doAsync {
            updatesList = getAllUpdates()
            uiThread {
                adapter.setList(updatesList)
                updates_RV.adapter = adapter
                updates_RV_swipe.isRefreshing = false
            }
        }
    }

    private fun getAllUpdates(): ArrayList<Update> {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://effervescence18-6e63f.firebaseio.com/updates.json")
                .build()
        val response = client.newCall(request).execute()

        val updatesList = ArrayList<Update>()
        if (response.isSuccessful) {
            val responseBody = JSONObject(response.body()?.string())
            val keys = responseBody.keys()

            while (keys.hasNext()) {
                val currentKey = keys.next()
                val currentUpdate = responseBody.getJSONObject(currentKey)
                updatesList.add(Update(currentKey,
                        currentUpdate.optString("title"),
                        currentUpdate.optString("description"),
                        currentUpdate.optLong("timestamp")))
            }
        } else {
            Toast.makeText(context, "Connection broke", Toast.LENGTH_SHORT).show()
        }
        return updatesList
    }
}
