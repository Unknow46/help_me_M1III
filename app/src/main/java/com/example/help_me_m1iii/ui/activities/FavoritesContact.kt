package com.example.help_me_m1iii.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.ui.fragments.FragmentFavoritesContact
import kotlinx.android.synthetic.main.favorites_contacts.*

class FavoritesContact() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_contacts)
        initializeFragmentRecyclerView()
    }

    fun initializeFragmentRecyclerView(){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_favorite_container, FragmentFavoritesContact.newInstance(), FragmentFavoritesContact.TAG).commit()
    }


}