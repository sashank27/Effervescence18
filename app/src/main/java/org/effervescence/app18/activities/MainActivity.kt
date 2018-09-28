package org.effervescence.app18.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_contents.*
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import org.effervescence.app18.R
import org.effervescence.app18.adapters.MainMenuAdapter
import org.effervescence.app18.fragments.HomeFragment
import org.effervescence.app18.fragments.InfoFragment
import org.effervescence.app18.fragments.UpdatesFragment
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mTitles: ArrayList<String>
    private lateinit var mMenuAdapter: MainMenuAdapter
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        mTitles = ArrayList(Arrays.asList(*resources.getStringArray(R.array.menu_items_array)))
        goToFragment(HomeFragment(), false)
        initializeDrawer()
        handleDrawerMenu()
    }

    private fun handleDrawerMenu() {

        title = "Home"
        mMenuAdapter = MainMenuAdapter(mTitles)

        main_menu.setOnMenuClickListener(object : DuoMenuView.OnMenuClickListener {
            override fun onOptionClicked(position: Int, objectClicked: Any?) {
                if (position != 1) {
                    title = mTitles[position]
                    mMenuAdapter.setSelectedView(position)

                }

                if (currentPage != position) {
                    when (position) {
                        0 -> {
                            currentPage = 0
                            goToFragment(HomeFragment(), false)
                        }
                        1 -> {
                            Handler().postDelayed({
                                val intent = Intent(this@MainActivity, EventsActivity::class.java)
                                startActivity(intent)
                            }, 300)

                        }
                        2 -> {
                            toast("ProShows Fragment")

                        }
                        3 -> {
                            goToFragment(UpdatesFragment(), false)
                            currentPage = 3
                        }
                        4 -> {
                            goToFragment(InfoFragment(), false)
                            currentPage = 4
                        }
                        else -> toast("Home Fragment")
                    }
                }

                main_drawer_layout.closeDrawer()
            }

            override fun onHeaderClicked() {
                toast("Clicked Header")
            }

            override fun onFooterClicked() {
                toast("Clicked Footer")
            }
        })

        main_menu.adapter = mMenuAdapter
        mMenuAdapter.setSelectedView(0)
    }

    private fun goToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack)
            transaction.addToBackStack(null)

        Handler().postDelayed({
            transaction.replace(R.id.main_container, fragment).commit()
        }, 300)
    }


    private fun initializeDrawer() {

        val drawerToggle = DuoDrawerToggle(this, main_drawer_layout, toolbar_main,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        main_drawer_layout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()

        main_drawer_layout.closeDrawer()
    }
}
