package com.example.help_me_m1iii.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.ui.models.Contacte
import kotlinx.android.synthetic.main.items_contact.view.*



class ContactAdapter(private val items: MutableList<Contacte>, private val clickListener: (Contacte) -> Unit) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView =
            LayoutInflater.from(parent.context).inflate(R.layout.items_contact, parent, false)
        return ViewHolder(lineView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAndVersion(items[position], clickListener)


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceAsColor")
        fun bindAndVersion(contactes: Contacte, clickListener: (Contacte) -> Unit) {
            with(contactes) {
                itemView.contact_name.text = name
                itemView.phone_number.text = phone_number

                itemView.setOnClickListener {
                    clickListener(contactes)
                }
            }
        }

    }
}
