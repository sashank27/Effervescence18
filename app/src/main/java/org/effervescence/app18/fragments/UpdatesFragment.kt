package org.effervescence.app18.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.esotericsoftware.minlog.Log.debug
import kotlinx.android.synthetic.main.fragment_updates.*
import okhttp3.OkHttpClient
import org.effervescence.app18.R
import org.effervescence.app18.adapters.UpdatesAdapter
import org.effervescence.app18.models.Update
import org.effervescence.app18.utils.AppDB
import org.jetbrains.anko.debug
import org.json.JSONObject


class UpdatesFragment : Fragment() {

    private var mUpdatesList = ArrayList<Update>()
    private lateinit var mUpdatesAdapter: UpdatesAdapter
    private lateinit var mClient: OkHttpClient
    private lateinit var appDB: AppDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mClient = OkHttpClient()

        updates_RV_swipe.setOnRefreshListener { updateRecyclerView() }
        appDB = AppDB.getInstance(context!!)
        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        updates_RV_swipe.isRefreshing = true
        updates_RV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mUpdatesList = ArrayList(appDB.getAllUpdates())
        if (mUpdatesList.size == 0) {
            emptyList_TV.visibility = View.VISIBLE
            updates_RV.visibility = View.GONE
        }
        mUpdatesAdapter = UpdatesAdapter(context!!, mUpdatesList)
        updates_RV.adapter = mUpdatesAdapter
        updates_RV_swipe.isRefreshing = false
    }

    private fun fetchLatestUpdates() {
        if (!isNetworkConnectionAvailable()) {
            Toast.makeText(context, "Check your internet connection.", Toast.LENGTH_SHORT).show()
            updates_RV_swipe.isRefreshing = false
            return
        }
        AndroidNetworking.get("https://effervescence18-6e63f.firebaseio.com/updates.json")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        if (response != null) {
                            mUpdatesList.clear()
                            val keys = response.keys()
                            while (keys.hasNext()) {
                                val currentKey = keys.next()
                                val childObj = response.getJSONObject(currentKey)
                                if (childObj != null) {
                                    val newNotification = Update(
                                            description = childObj.getString("description"),
                                            senderName = childObj.getString("senderName"),
                                            senderEmail = childObj.getString("senderEmail"),
                                            timestamp = childObj.getLong("timestamp"),
                                            title = childObj.getString("title"),
                                            eventID = childObj.getLong("eventID"),
                                            verified = childObj.getBoolean("verified")
                                    )
                                    if (newNotification.verified) mUpdatesList.add(newNotification)
                                }
                            }
                            appDB.storeUpdates(mUpdatesList)
                            mUpdatesList = ArrayList(appDB.getAllUpdates())
                            if (mUpdatesList.size == 0) {
                                emptyList_TV.visibility = View.VISIBLE
                                updates_RV.visibility = View.GONE
                            }
                        } else {
                            emptyList_TV.visibility = View.VISIBLE
                            updates_RV.visibility = View.GONE
                        }
                        updates_RV_swipe.isRefreshing = false
                    }
                    override fun onError(anError: ANError?) {
                        //Show connectivity problem text
                        updates_RV_swipe.isRefreshing = false
                    }
                })
    }

    private fun updateRecyclerView() {
        fetchLatestUpdates()
        mUpdatesAdapter.setList(mUpdatesList)
    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return if (isConnected) {
            debug("Network Connected")
            true
        } else {
            debug("Network not Connected")
            false
        }
    }


    override fun onDetach() {
        super.onDetach()
        AndroidNetworking.cancel("getUpdatesRequest")
    }
}
