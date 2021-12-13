package com.ksolves.newsapporg.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ksolves.newsapporg.Fragments.*
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.activity_mainn.*


class MainActivityy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainn)

        bottomNavigation.setItemSelected(R.id.general, true)

        setupFragment(GeneralFragment())

        //open fragment
        bottomNavigation.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.bussiness -> setupFragment(BusinessFragment())
                    R.id.entertainment -> setupFragment(EntertainmentFragment())
                    R.id.general -> setupFragment(GeneralFragment())
                    R.id.health -> setupFragment(HealthFragment())
                    R.id.science -> setupFragment(ScienceFragment())
                    R.id.sports -> setupFragment(SportsFragment())
                    R.id.technology -> setupFragment(TechnologyFragment())
                    R.id.favourite -> setupFragment(FavouriteFragment())
                    R.id.search -> setupFragment(SearchFragment())
                }
            }
        })
    }

    private fun setupFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, fragment)
        transaction.commit()
    }

}