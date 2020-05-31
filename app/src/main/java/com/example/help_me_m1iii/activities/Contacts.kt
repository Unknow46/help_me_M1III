package com.example.help_me_m1iii.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.fragments.FragmentContacte



class Contacts : AppCompatActivity() {


    companion object {
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacte)
        checkPermission()
    }


    private fun checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
        } else {
            initializeFragmentRecyclerView()
        }
    }

    fun initializeFragmentRecyclerView(){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentContacte.newInstance(), FragmentContacte.TAG).commit()
        }

    }