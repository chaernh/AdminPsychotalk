package com.example.firebaseauth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.Model.User
import com.google.firebase.firestore.FirebaseFirestore


class ConsultantActivity : AppCompatActivity() {
    var userList = ArrayList<User>()
    var db:FirebaseFirestore? = FirebaseFirestore.getInstance()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultant)
        var listview:ListView = findViewById(R.id.listConsultant)
        db!!.collection("Users").whereEqualTo("role","KONSULTAN")
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
        intent.putExtra("role","KONSULTAN")
        startActivity(intent)
    }
}
