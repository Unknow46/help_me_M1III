package com.example.help_me_m1iii.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.models.Contacte
import kotlinx.android.synthetic.main.favorites_items_contact.view.*
import kotlinx.android.synthetic.main.items_contact.view.*
import kotlinx.android.synthetic.main.items_contact.view.contact_name
import kotlinx.android.synthetic.main.items_contact.view.phone_number

class FavoritesContactAdapter(private val items: MutableList<Contacte>, private val clickListener: (Contacte) -> Unit) : RecyclerView.Adapter<FavoritesContactAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.favorites_items_contact, parent, false)
        return FavoritesViewHolder(lineView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bindAndVersion(items[position], clickListener)
    }

    class FavoritesViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceAsColor")
        fun bindAndVersion(contactes: Contacte, clickListener: (Contacte) -> Unit) {
            with(contactes) {
                itemView.contact_name.text = name
                itemView.phone_number.text = phone_number

                itemView.delete_contact.setOnClickListener {
                    clickListener(contactes)}
            }
        }
    }

}
