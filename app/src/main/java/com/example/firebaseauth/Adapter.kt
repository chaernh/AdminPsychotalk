package com.example.firebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(val mCtx: Context, val layoutResId: Int, val list: ArrayList<Users> )
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textJudul = view.findViewById<TextView>(R.id.textJudul)
        val textBerita = view.findViewById<TextView>(R.id. textBerita)
        val textNama = view.findViewById<TextView>(R.id. textNama)

        val user = list[position]

        textJudul.text = user.judul
        textBerita.text = user.berita
        textNama.text = user.nama

        return view

    }
}