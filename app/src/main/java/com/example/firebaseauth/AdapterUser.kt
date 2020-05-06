package com.example.firebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauth.Model.User

class AdapterUser(private val context: Context,
                  private val dataSource: ArrayList<User>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: RecyclerView.ViewHolder

        val rowView = inflater.inflate(R.layout.layout_user, parent, false)

        val titleTextView = rowView.findViewById(R.id.user_list_title) as TextView

        val subtitleTextView = rowView.findViewById(R.id.user_list_subtitle) as TextView

        val detailTextView = rowView.findViewById(R.id.user_list_detail) as TextView

        val thumbnailImageView = rowView.findViewById(R.id.user_list_thumbnail) as ImageView
        // 1
        val user = getItem(position) as User


        titleTextView.text = user.name
        subtitleTextView.text = user.email
        detailTextView.text = user.role


        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}