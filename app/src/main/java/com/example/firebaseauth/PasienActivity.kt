package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.firebaseauth.Model.User
import com.google.firebase.firestore.FirebaseFirestore

class PasienActivity : AppCompatActivity() {
    var userList = ArrayList<User>()
    var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)
        var listview: ListView = findViewById(R.id.listConsultant)
        db!!.collection("Users").whereEqualTo("role","PASIEN")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val user: User = document.toObject(User::class.java)
                        userList.add(user)
                    }
                    val adapter = AdapterUser(this,userList)
                    listview.adapter = adapter
                } else {

                }
            }
        listview.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id: Long ->
            val user = userList[position]
            val intent = Intent(this,DetailUserActivity::class.java)
            intent.putExtra("uid",user.uid)
            startActivity(intent)
        }
    }

    fun btnTambah(view: View) {
        val intent = Intent(this, Registration::class.java)
        intent.putExtra("role","PASIEN")
        startActivity(intent)
    }
}
