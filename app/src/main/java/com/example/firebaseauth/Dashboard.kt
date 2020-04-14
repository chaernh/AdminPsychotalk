package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.chaer.adminpsychotalk.Model
import com.chaer.adminpsychotalk.MyAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var listView = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<Model>()

        list.add(Model("Pasien", "Atur dan kelola seluruh pasienmu", R.drawable.ic_launcher_background))
        list.add(Model("Konsultan", "Atur dan kelola seluruh konsultanmu", R.drawable.ic_launcher_background))
        list.add(Model("Blog", "Atur dan kelola seluruh blogmu", R.drawable.ic_launcher_background))
        list.add(Model("Kritik & Saran", "Cek seluruh kritik & saran yang ada", R.drawable.ic_launcher_background))

        listView.adapter = MyAdapter( this, R.layout.row, list)

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id: Long ->
            if (position == 0){
                Toast.makeText(this@Dashboard, "You click Pasien!", Toast.LENGTH_LONG).show()
            }
            else if (position == 1){
                Toast.makeText(this@Dashboard, "You click Konsultan!", Toast.LENGTH_LONG).show()
            }
            else if (position == 2){
                Toast.makeText(this@Dashboard, "You click Blog!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Blog::class.java)
                startActivity(intent)
            }
            else if (position == 3){
                Toast.makeText(this@Dashboard, "You click Kritik & Saran!", Toast.LENGTH_LONG).show()
            }
        }

        toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notification -> {
                Toast.makeText(this, "Notification clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_change_password -> {
                Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
//                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
                auth.signOut()
                val intent =
                    Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}