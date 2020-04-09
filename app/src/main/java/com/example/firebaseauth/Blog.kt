package com.example.firebaseauth

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_blog.*

class Blog : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Users>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        ref = FirebaseDatabase.getInstance().getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        val adapter = Adapter(this@Blog,R.layout.users,list)
        listView.adapter = adapter

        btnSave.setOnClickListener {
            savedata()
//            val intent = Intent (this, show::class.java)
//            startActivity(intent)
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(position: DatabaseError) {

            }
            override fun onDataChange(position: DataSnapshot) {
                if (position!!.exists()){

                    for (h in position.children){
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(applicationContext,R.layout.users,list)
                    listView.adapter = adapter
                }
            }
        })
    }

    private fun savedata() {
        val judul = inputJudul.text.toString()
        val berita = inputBerita.text.toString()
        val nama = inputNama.text.toString()

        val user = Users(judul,berita,nama)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            inputJudul.setText("")
            inputBerita.setText("")
            inputNama.setText("")
        }
    }
}