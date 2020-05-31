package com.example.help_me_m1iii.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.fragments.FragmentFavoritesContact
import kotlinx.android.synthetic.main.favorites_contacts.*

class FavoritesContact() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_contacts)
        initializeFragmentRecyclerView()

        add_contact.setOnClickListener {
            var fragment = supportFragmentManager.findFragmentById(R.id.fragment_favorite_container) as FragmentFavoritesContact
            fragment.addingNewFavorites()
        }

    }

    fun initializeFragmentRecyclerView(){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_favorite_container, FragmentFavoritesContact.newInstance(), FragmentFavoritesContact.TAG).commit()
    }


}