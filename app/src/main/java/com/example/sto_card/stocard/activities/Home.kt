package com.example.sto_card.stocard.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.sto_card.R
import com.example.sto_card.stocard.fragements.*
import com.example.sto_card.stocard.modals.SharedPrefManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.util.*


class Home : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar:Toolbar
    var context: Context? = null




    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val SHARED_PREF_NAME = "my_shared_preff"
        val sharedPreferences = context?.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )


        val sharedPreference = this?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        var token ="Bearer " + (sharedPreference?.getString("token", "defaultName"))
        Log.e("token!!!", token)
        val nm = sharedPreference?.getString("name", "defaultName")
        val pt =  sharedPreference?.getString("user_img", "defaultName")


         toolbar= findViewById(R.id.toolbar_main)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        var header=navigationView.getHeaderView(0)
        header.nav_header_textView.setText(nm)
        header.nav_header_imageView.load(pt?.toUri())

        openFragment(StoreFragment())

   }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
//
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.Change_Password -> {
            openFragment(ChangePasswordFragment())

        }

        R.id.Change_Pin->openFragment(ChangePinFragment())
    }

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Profile -> {
                openFragment(ProfileFragment())

            }
            R.id.Home ->
                openFragment(StoreFragment())
                //Toast.makeText(this, "Clicked item one", Toast.LENGTH_SHORT).show()
            R.id.Chat_bot ->
                openFragment(ChatBotFragment())
            R.id.Logout -> {

                SharedPrefManager.getInstance(applicationContext).clear()
                startActivity(Intent(this, IntroScreen::class.java))
                true
            }
            R.id.Have_A_code -> {
                openFragment(EnterRefferalCodeFragment())
            }
            R.id.Map -> {
               openFragment(MapFragment())
            }
            R.id.Change_Pin->
            {
                openFragment(ChangePinFragment())
            }
            R.id.Invite_User -> {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.example.stocardapp"
                    )
                    type = "text/plain"
                }
                startActivity(shareIntent)
                // openFragment(EnterRefferalCodeFragment())
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {

    }


}