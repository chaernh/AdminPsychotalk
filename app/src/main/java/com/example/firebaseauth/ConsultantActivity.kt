package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.Model.User
import com.google.firebase.firestore.FirebaseFirestore


class ConsultantActivity : AppCompatActivity() {
   var userList = ArrayList<User>()
    var db:FirebaseFirestore? = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultant)
        db!!.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val user: User = document.toObject(User::class.java)
                        userList.add(user)
                    }
                    var listview:ListView = findViewById(R.id.listConsultant)
                    val adapter = AdapterUser(this,userList)
                    listview.adapter = adapter
                } else {

                }
            }
    }

    fun btnTambah(view: View) {
        val intent = Intent(this, Registration::class.java)
        startActivity(intent)
    }
}
