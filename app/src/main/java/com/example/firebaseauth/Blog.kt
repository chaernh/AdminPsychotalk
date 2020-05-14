package com.example.firebaseauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_blog.*
import kotlinx.android.synthetic.main.custom_dialog_blog.view.*

class Blog : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Users>
    lateinit var listViewBlog: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        ref = FirebaseDatabase.getInstance().getReference("BLOG")
        list = mutableListOf()
        listViewBlog = findViewById(R.id.listViewBlog)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = Adapter(this@Blog,R.layout.list_view_blog,list)
                    listViewBlog.adapter = adapter
                }
            }
        })
    }
    fun btn_Insert_Blog(view: View) {

        val mView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_blog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mView)

        val mAlertDialog = mBuilder.show()
        val myRef = FirebaseDatabase.getInstance().getReference("BLOG")

        mView.btn_proceed.setOnClickListener {
            var judul = mView.inputJudul.text.toString()
            var berita = mView.inputBerita.text.toString()
            var nama = mView.inputNama.text.toString()

            var blogId = myRef.push().key.toString()
            var user = Users(blogId,judul,berita,nama)

            myRef.child(blogId).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show()
                mView.inputJudul.setText("")
                mView.inputBerita.setText("")
                mView.inputNama.setText("")
            }

            mAlertDialog.dismiss()
        }
        mView.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}