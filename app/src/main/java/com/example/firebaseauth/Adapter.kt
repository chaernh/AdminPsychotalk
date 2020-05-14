package com.example.firebaseauth

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.custom_dialog_info_blog.view.*

class Adapter(val mCtx: Context, val layoutResId: Int, val list: MutableList<Users>)
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textJudul = view.findViewById<TextView>(R.id. textJudul)
        val textBerita = view.findViewById<TextView>(R.id. textBerita)
        val textNama = view.findViewById<TextView>(R.id.textNama)

        val linearUpdate = view.findViewById<LinearLayout>(R.id.LinearBlog)

        val user = list[position]

        textBerita.text = user.berita
        textJudul.text = user.judul
        textNama.text = user.nama

        linearUpdate.setOnClickListener { showUpdateDeleteDialog(user) }


        return view

    }

    private fun showUpdateDeleteDialog(user: Users) {
        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.custom_dialog_info_blog, null)

        val textJudul = view.findViewById<EditText>(R.id.infoInputJudul)
        val textBerita = view.findViewById<EditText>(R.id.infoInputBerita)
        val textNama = view.findViewById<EditText>(R.id.infoInputNama)

        textJudul.setText(user.judul)
        textBerita.setText(user.berita)
        textNama.setText(user.nama)

        builder.setView(view)

        val alert = builder.create()
        alert.show()

        view.btn_update.setOnClickListener {
            val dbProduk = FirebaseDatabase.getInstance().getReference("BLOG")

            val judul = textJudul.text.toString().trim()
            val berita = textBerita.text.toString().trim()
            val nama = textNama.text.toString().trim()

            if (judul.isEmpty()){
                textJudul.error = "Wajib Masukkan Judul Berita"
                textJudul.requestFocus()
                return@setOnClickListener
            }

            if (berita.isEmpty()){
                textBerita.error = "Wajib Masukkan Informasi Berita"
                textBerita.requestFocus()
                return@setOnClickListener
            }

            if (nama.isEmpty()){
                textNama.error = "Wajib Masukkan Nama Author"
                textNama.requestFocus()
                return@setOnClickListener
            }

            val user = Users(user.id,judul,berita,nama)

            dbProduk.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Berita Success Update", Toast.LENGTH_SHORT).show()

            }

            alert.dismiss()
        }

        view.btn_delete.setOnClickListener {
            val progressDialog = ProgressDialog(context)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Deleting...")
            progressDialog.show()

            val dbProduk = FirebaseDatabase.getInstance().getReference("USERS")
            dbProduk.child(user.id).removeValue()
            Toast.makeText(mCtx,"Deleted Success!!", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}