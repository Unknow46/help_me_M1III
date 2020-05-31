package com.example.help_me_m1iii.ui.login.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.ui.fragments.FragmentContacte
import com.example.help_me_m1iii.ui.fragments.HomeFragments
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
