package org.effervescence.app18.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_detail.*
import kotlinx.android.synthetic.main.organizer_layout.view.*
import org.effervescence.app18.R
import org.effervescence.app18.models.Event
import org.effervescence.app18.utils.AppDB
import java.text.SimpleDateFormat
import java.util.*

class EventDetailActivity : AppCompatActivity() {
    private lateinit var event : Event

    private lateinit var appDB: AppDB
    private lateinit var callNumber: String
    private val requestCode = 123

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        appDB = AppDB.getInstance(this)
        event = intent.getParcelableExtra("event")


        setupInitialView()
        if (appDB.isBookmarked(event.id)) {
            isBookmarked = true
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark))
        }


        fab.setOnClickListener { view ->

            if(isBookmarked){
                if (appDB.removeBookmark(event.id)) {
                    Snackbar.make(view, "The event is no longer bookmarked", Snackbar.LENGTH_SHORT)
                            .show()

                    isBookmarked = false
                    fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border))
                } else {
                    Snackbar.make(view, "Sorry an error occurred", Snackbar.LENGTH_SHORT)
                            .show()
                }
            } else{
                if (appDB.addBookmark(event.id)) {
                    Snackbar.make(view, "Event Bookmarked Successfully", Snackbar.LENGTH_SHORT)
                           .show()

                    isBookmarked = true
                    fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark))
                } else {
                    Snackbar.make(view, "Sorry an error occurred", Snackbar.LENGTH_SHORT)
                           .show()
                }
            }

        }
    }

    private fun setupInitialView() {
        toolbar_layout.title = event.name
        //toolbar_layout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))


        //eventNameTextView.text = event.name
        eventLocationTextView.text = event.location

        if(event.timestamp < 100L){
            eventLocationTextView.text = "Online"
            eventDateTextView.visibility = View.GONE
            dateImage.visibility = View.GONE
            eventTimeTextView.visibility = View.GONE
            clockImage.visibility = View.GONE
        }
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/India"))
        calendar.timeInMillis = event.timestamp.times(1000L)


        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("Asia/India")
        eventTimeTextView.text = sdf.format(calendar.time)

        sdf.applyPattern("MMMM d, yyyy")
        eventDateTextView.text = sdf.format(calendar.time)

        Glide.with(backdrop_Event).load(event.imageUrl).into(backdrop_Event).onLoadStarted(ContextCompat.getDrawable(this, R.drawable.placeholder_event))

        overviewTextView.text = event.description



        if(event.facebookEventLink.isEmpty()){
            facebookCardView.visibility = View.GONE
        }else{
            eventFacebookLinkTextView.text = event.facebookEventLink
            facebookCardView.setOnClickListener {
                Log.v("Clciked", "Facebook clicked")
                val url = event.facebookEventLink
                try {
                    baseContext.packageManager.getPackageInfo("com.facebook.katana", 0)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url))
                    baseContext.startActivity(intent)
                } catch (e: Exception) {
                    val builder = CustomTabsIntent.Builder()
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(this, Uri.parse(url))
                }
            }
        }

        if(event.organizers.isNotEmpty()){
            event.organizers.map {
                val view = View.inflate(this, R.layout.organizer_layout, null)
                view.organizerName.text = it.name
                callNumber = it.phoneNumber.toString()
                view.organizerNumber.text = callNumber
                view.callButton.setOnClickListener {
                    callNumber = it.tag as String
                    Log.v("Clicked", "$callNumber")
                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                requestCode)
                    } else
                        startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
                }
                view.callButton.tag = callNumber
                organizerLinearLayout.addView(view)
            }
        }else {
            tv2.visibility = View.GONE
            organizerLinearLayout.visibility = View.GONE
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            requestCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + callNumber)))
            } else {
                Log.d("TAG", "Call Permission Not Granted")
            }
        }
    }

}
