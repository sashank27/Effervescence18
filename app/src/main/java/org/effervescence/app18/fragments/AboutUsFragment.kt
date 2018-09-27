package org.effervescence.app18.fragments


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_about_us.view.*

import org.effervescence.app18.R


class AboutUsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = activity!!
        view.fb_icon.setOnClickListener {
            val id = "effervescence.iiita/"
            val url = "https://www.facebook.com/$id"
            try {
                context.packageManager.getPackageInfo("com.facebook.katana", 0)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
                context.startActivity(intent)
            } catch (e: Exception) {
                openChromeTab(context,url)
            }
        }

        view.twitter_icon.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("twitter://user?screen_name=goeffervescence"))
                startActivity(intent)

            } catch (e: Exception) {
                openChromeTab(context,"https://twitter.com/goeffervescence")
            }

        }

        view.insta_icon.setOnClickListener { openChromeTab(context,"https://www.instagram.com/goeffervescence/") }
        view.youtube_icon.setOnClickListener { openChromeTab(context,"https://www.youtube.com/channel/UCSKd_5A0v36U5Bt0y-SDJWg") }
        view.tv_web.setOnClickListener { openChromeTab(context,"https://www.effe.org.in") }
    }

    private fun openChromeTab(context: Context, url: String){
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

}
