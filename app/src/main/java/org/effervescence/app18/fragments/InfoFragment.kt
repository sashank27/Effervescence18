package org.effervescence.app18.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

import org.effervescence.app18.R
import org.effervescence.app18.fragments.infoFragmenet.AboutUsFragment
import org.effervescence.app18.fragments.infoFragmenet.DevelopersFragment
import org.effervescence.app18.fragments.infoFragmenet.SponsorsFragment
import org.effervescence.app18.fragments.infoFragmenet.TeamFragment


class InfoFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfSubFragments = ArrayList<Fragment>()
        listOfSubFragments.add(TeamFragment())
        listOfSubFragments.add(DevelopersFragment())
        listOfSubFragments.add(SponsorsFragment())
        listOfSubFragments.add(AboutUsFragment())

        val mPagerAdapter = InfoAdapter(activity!!.supportFragmentManager, listOfSubFragments)
        view.pager.adapter = mPagerAdapter
        view.tab_layout.setupWithViewPager(view.pager)
    }

    override fun onResume() {
        super.onResume()
    }

    private inner class InfoAdapter internal constructor(fragmentManager: FragmentManager,
                                                         listOfSubFragments: List<Fragment>) : FragmentStatePagerAdapter(fragmentManager){
        private val titles = arrayOf("TEAM", "DEVELOPERS", "SPONSORS", "ABOUT")
        internal var fragments: ArrayList<Fragment> = ArrayList()

        init {
            fragments.addAll(listOfSubFragments)
        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int = titles.size

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
