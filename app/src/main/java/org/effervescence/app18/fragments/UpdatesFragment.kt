package org.effervescence.app18.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.fragment_updates.*
import okhttp3.OkHttpClient
import org.effervescence.app18.R
import org.effervescence.app18.adapters.UpdatesAdapter
import org.effervescence.app18.models.Update
import org.effervescence.app18.utils.AppDB
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
        mUpdatesList = getAllUpdatesAndUpdateList()
        mUpdatesAdapter = UpdatesAdapter(context!!, mUpdatesList)
        updates_RV.adapter = mUpdatesAdapter
        updates_RV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun getAllUpdatesAndUpdateList(): ArrayList<Update> {
        AndroidNetworking.get("https://effervescence18-6e63f.firebaseio.com/updates.json")
                .setTag("getUpdatesRequest")
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
                            mUpdatesAdapter.setList(mUpdatesList)
                        } else { /*Show empty list image */ }
                    }
                    override fun onError(anError: ANError?) {
                        //Show connectivity problem text
                    }
                })
        return mUpdatesList
    }

    private fun updateRecyclerView() {
        updates_RV_swipe.isRefreshing = true
        mUpdatesList = getAllUpdatesAndUpdateList()
        mUpdatesAdapter.setList(mUpdatesList)
        updates_RV_swipe.isRefreshing = false
    }

    override fun onDetach() {
        super.onDetach()
        AndroidNetworking.cancel("getUpdatesRequest")
    }
}
