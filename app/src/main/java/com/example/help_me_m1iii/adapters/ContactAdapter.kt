package com.example.help_me_m1iii.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.models.Contacte
import kotlinx.android.synthetic.main.items_contact.view.*

class ContactAdapter(val items: MutableList<Contacte>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.items_contact, parent, false)
        return ViewHolder(lineView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAndVersion(items[position])
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view), AdapterView.OnItemClickListener {

        fun bindAndVersion(nom: Contacte) {
            with(nom) {
                itemView.contact_name.text = "$name"
            }
        }

        override fun onItemClick(p0: AdapterView<*>?, view: View?, p2: Int, p3: Long) {
            //TODO
        }

    }

}