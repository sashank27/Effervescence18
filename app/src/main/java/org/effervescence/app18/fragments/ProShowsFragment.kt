package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kannan.glazy.GlazyCard
import com.kannan.glazy.Utils
import com.kannan.glazy.pager.GlazyFragmentPagerAdapter
import com.kannan.glazy.transformers.GlazyPagerTransformer
import com.kannan.glazy.views.GlazyImageView.ImageCutType
import kotlinx.android.synthetic.main.fragment_pro_shows.*

import org.effervescence.app18.R

class ProShowsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pro_shows, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(view)
    }

    private fun setupViewPager(view: View) {
        val resources = context!!.resources

        val imgSunidhi = resources.getIdentifier("celebnight", "drawable", activity!!.packageName)
        val imgHumour = resources.getIdentifier("humournight", "drawable", activity!!.packageName)
        val imgEDM = resources.getIdentifier("edmnight", "drawable", activity!!.packageName)
        val imgRock = resources.getIdentifier("localtrain", "drawable", activity!!.packageName)

        val desc_celeb = "They say that when you become a celebrity ,you own the world and the world owns you. " +
                "So get ready to own the world as the bar has been raised up higher. After superstars like Neha " +
                "Kakkar,Benny Dayal and Farhan Akhtar, Effervescence’18 proudly presents to you Sunidhi Chauhan performing this October !"

        val desc_edm = "EDM isn’t just about drops and rave, it is the form of music that has something to offer" +
                " to everyone. Get ready to match you heartbeats with the beats and drops and dance your heart out " +
                "with famous ZEPHYRTONE all set to raise the electric mood in the atmosphere!"

        val desc_kavyom = "Gear up to enjoy the Infinite Sky of Poetry at Kavyom, being held for the very first " +
                "time in IIIT Allahabad. Relish an indelible and a stupendous evening with the one and only, Kumar " +
                "Vishwas. It is doubtlessly going to be the most congenial event for all poetry lovers out there."

        val desc_humour = "The best thing about comedians apart from their wit and talent, is their confidence to bring " +
                "out the mirth in small things! Known as one of the Rising Stars of Comedy in India, is one such comedian," +
                " Aakash Gupta! We're bringing in the pros, to help you forget your woes! " +
                "So, prepare to burst your lungs out and laugh to the rant of the leading stand up comedian - Mr Aakash Gupta on " +
                "the Humor Night of Effervescence'18!"

        val desc_local = "Now it’s time to relive and cherish some memories yet again, for Effervescence recreates it back, as we " +
                "present to you the night which is going to mark the enigma of ultimate fiesta, the one and only, The Local Train. " +
                "Now as the house is up and the fire within rises, join in for some surreal experiences and invigoration of wonder for the " +
                "grounds of Effervescence would be waiting for you!"

        val pagerAdapter = GlazyFragmentPagerAdapter(childFragmentManager, context)

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("CELEB NIGHT")
                        .withSubTitle("7th October")
                        .withDescription(desc_celeb.toUpperCase())
                        .withImageRes(imgSunidhi)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutHeightDP(50)
        )

        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("ROCK NIGHT")
                        .withSubTitle("6th October")
                        .withDescription(desc_local.toUpperCase())
                        .withImageRes(imgRock)
                        .withImageCutType(ImageCutType.ARC)
                        .withImageCutHeightDP(50)
        )


        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("HUMOUR NIGHT")
                        .withSubTitle("6th October")
                        .withDescription(desc_humour.toUpperCase())
                        .withImageRes(imgHumour)
                        .withImageCutType(ImageCutType.LINE_POSITIVE)
                        .withImageCutHeightDP(50)
        )


        pagerAdapter.addCardItem(
                GlazyCard()
                        .withTitle("EDM NIGHT")
                        .withSubTitle("5th October")
                        .withDescription(desc_edm.toUpperCase())
                        .withImageRes(imgEDM)
                        .withImageCutType(ImageCutType.WAVE)
                        .withImageCutHeightDP(50)
        )

        pager.adapter = pagerAdapter
        pager.pageMargin = Utils.dpToPx(context, 25)
        pager.setPageTransformer(false, GlazyPagerTransformer())
    }


}
