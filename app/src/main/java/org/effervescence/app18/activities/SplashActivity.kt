package org.effervescence.app18.activities

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.effervescence.app18.R
import org.effervescence.app18.models.*
import org.effervescence.app18.utils.AnimatorListenerAdapter
import org.effervescence.app18.utils.AppDB
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.*
import org.json.JSONObject
import java.sql.Timestamp


class SplashActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var sharedPrefs: SharedPreferences
    private var time = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPrefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        animationView.setAnimation("loading_spinner.json")
        animationView.playAnimation()
        animationView.loop(true)

        time = System.currentTimeMillis()
        val lastTime = sharedPrefs.getLong("lastupdated", 0)
        Log.e("Skip", "$time and $lastTime")
        when {
            isNetworkConnectionAvailable() -> {
                if((time - lastTime) > 172800000){
                    fetchLatestData()
                    sharedPrefs.edit().putLong("lastupdated",time).commit()
                }else{
                    Log.e("Skip", "Skipping")
                    startActivity<MainActivity>()
                    finish()
                }

            }
            !sharedPrefs.getBoolean("firstrun", true) -> {
                showFinishedAnimation()
            }
            else -> {
                animationView.cancelAnimation()
                showAlert()
            }
        }
    }

    private fun fetchLatestData() {
        doAsync {
            val appDB = AppDB.getInstance(this@SplashActivity)
            val client = OkHttpClient()


            try {
                val request = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/events.json")
                        .build()


                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val list = Moshi.Builder()
                            .build()
                            .adapter<Array<Event>>(Array<Event>::class.java)
                            .fromJson(response.body()?.string())

                    if (list != null) {
                        appDB.storeEvents(events = list.toList())
                    }
                }

                val request2 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/sponsors.json")
                        .build()
                val response2 = client.newCall(request2).execute()
                if (response2.isSuccessful) {
                    val arrayOfSponsors = Moshi.Builder().build()
                            .adapter<Array<Sponsor>>(Array<Sponsor>::class.java)
                            .fromJson(response2.body()?.string())
                    if (arrayOfSponsors != null) {
                        appDB.storeSponsors(arrayOfSponsors.toList())
                    }
                }

                val request3 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/team.json")
                        .build()
                val response3 = client.newCall(request3).execute()

                if (response3.isSuccessful) {
                    val teamArray = Moshi.Builder().build()
                            .adapter<Array<Person>>(Array<Person>::class.java)
                            .fromJson(response3.body()?.string())

                    if (teamArray != null) {
                        appDB.storeTeam(teamArray.toList())
                    }
                }

                val request4 = Request.Builder()
                        .url("https://effervescence-iiita.github.io/Effervescence17/data/developer.json")
                        .build()
                val response4 = client.newCall(request4).execute()

                if (response4.isSuccessful) {
                    val developersArray = Moshi.Builder().build()
                            .adapter<Array<Developer>>(Array<Developer>::class.java)
                            .fromJson(response4.body()?.string())

                    if (developersArray != null) {
                        appDB.storeDevelopers(developersArray.toList())
                    }
                }

                /*
                val request5 = Request.Builder()
                        .url("https://effervescence18-6e63f.firebaseio.com/updates.json")
                        .build()
                val response5 = client.newCall(request5).execute()

                if (response5.isSuccessful) {
                    val updatesList = ArrayList<Update>()
                    val responseBody = JSONObject(response5.body()?.string())
                    val keys = responseBody.keys()

                    while (keys.hasNext()) {
                        val currentKey = keys.next()
                        val currentUpdate = responseBody.getJSONObject(currentKey)
                        updatesList.add(Update(currentKey,
                                currentUpdate.optString("title"),
                                currentUpdate.optString("description"),
                                currentUpdate.optLong("timestamp")))
                    }
                    appDB.storeUpdates(updatesList)
                }
                */

                uiThread {
                    if (!response.isSuccessful || !response2.isSuccessful || !response3.isSuccessful || !response4.isSuccessful) {
                        if (sharedPrefs.getBoolean("firstrun", true)) {
                            showAlert()
                        } else {
                            showFinishedAnimation()
                        }
                    } else {
                        showFinishedAnimation()
                    }
                }
            } catch (exception: Exception) {
                uiThread {
                    error(exception)
                    animationView.cancelAnimation()
                    if (sharedPrefs.getBoolean("firstrun", true)) {
                        showAlert()
                    } else {
                        showFinishedAnimation()
                    }
                }
            }
        }
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No internet Connection")
        builder.setMessage("Please turn on internet connection to continue. Internet is needed at least once before running the app.")
        builder.setNegativeButton("close") { _, _ -> finish() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showFinishedAnimation() {
        animationView.setAnimation("checked_done.json")
        animationView.loop(false)
        animationView.playAnimation()
        animationView.addAnimatorListener(AnimatorListenerAdapter(
                onStart = { },
                onEnd = {
                    sharedPrefs.edit().putBoolean("firstrun", false).commit()
                    startActivity<MainActivity>()
                    finish()
                },
                onCancel = { },
                onRepeat = { }))
    }

    private fun isNetworkConnectionAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

}
