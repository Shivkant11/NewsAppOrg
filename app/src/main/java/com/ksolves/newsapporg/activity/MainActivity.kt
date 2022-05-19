package com.ksolves.newsapporg.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ksolves.newsapporg.fragments.*
import com.ksolves.newsapporg.R
import com.ksolves.newsapporg.utils.Shortcuts
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth






class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Shortcuts.setUp(applicationContext)
        bottomNavigation.setItemSelected(R.id.general, true)

        if ("SEARCH" == intent.action) {
            setupFragment(SearchFragment(),"")
        }
        else if("FAV" == intent.action) {
            setupFragment(FavouriteFragment(),"")
        }
        else {
            setupFragment(GeneralFragment(),"")
        }

        //open fragment

        bottomNavigation.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.bussiness -> setupFragment(BusinessFragment(), "business")
                    R.id.entertainment -> setupFragment(BusinessFragment(), "entertainment")
                    R.id.general -> setupFragment(GeneralFragment(),"")
                    R.id.health -> setupFragment(BusinessFragment(),"health")
                    R.id.science -> setupFragment(BusinessFragment(), "science")
                    R.id.sports -> setupFragment(BusinessFragment(), "sports")
                    R.id.technology -> setupFragment(BusinessFragment(), "technology")
                    R.id.favourite -> setupFragment(FavouriteFragment(),"")
                    R.id.search -> setupFragment(SearchFragment(),"")
                    R.id.bookmark ->  {val intent = Intent(this@MainActivity, BookmarkActivity::class.java)
                    startActivity(intent)}
                    R.id.sinout -> {
                        FirebaseAuth.getInstance().signOut()
                        val intentSinout = Intent(this@MainActivity, MultiLogin::class.java)
                        startActivity(intentSinout)}

                }
            }
        })
    }

    private fun setupFragment(fragment : Fragment, str : String) {
        val bundle  = Bundle()
        bundle.putString("str", str)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, fragment)
        fragment.arguments = bundle
        transaction.commit()
    }

}