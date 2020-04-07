package com.example.help_me_m1iii.ui.login.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.help_me_m1iii.R
import com.google.firebase.auth.FirebaseAuth


class home : AppCompatActivity() {
    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, PhoneAuthentication::class.java))
        }else {
            Toast.makeText(this, "Already Signed in :)", Toast.LENGTH_LONG).show()
        }
    }
}
