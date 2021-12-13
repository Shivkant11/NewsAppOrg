package com.ksolves.newsapporg.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.ksolves.newsapporg.adapters.Adapter
import com.ksolves.newsapporg.models.Article
import com.ksolves.newsapporg.Fragments.*
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var toggle :ActionBarDrawerToggle
    lateinit var adapter: Adapter
    private var articles = mutableListOf<Article>()
    lateinit var listNews : RecyclerView
    lateinit var container_ : ConstraintLayout
    lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listNews = findViewById(R.id.listNews)
        container_ = findViewById(R.id.container)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.bussiness -> replaceFragment(BusinessFragment())
                R.id.entertainment -> replaceFragment(EntertainmentFragment())
                R.id.general -> replaceFragment(GeneralFragment())
                R.id.health -> replaceFragment(HealthFragment())
                R.id.science -> replaceFragment(ScienceFragment())
                R.id.sports -> replaceFragment(SportsFragment())
                R.id.technology -> replaceFragment(TechnologyFragment())
            }
            true
        }

        replaceFragment(GeneralFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.fav -> replaceFragment(FavouriteFragment())
                R.id.home -> replaceFragment(GeneralFragment())
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment : Fragment){
        if (fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }
}