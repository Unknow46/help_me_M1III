package com.example.help_me_m1iii.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.help_me_m1iii.R
import kotlinx.android.synthetic.*

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragments : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var requestSendSms: Int = 2
    private var requestGetPosition: Int = 5
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val let = arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val intent = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse("smsto: 0603970213")
        intent.data = uri
        intent.putExtra("sms_body", "C'est un message d'alerte je suis en danger")
        */

        AlertButton.setOnClickListener {
            if((ActivityCompat.checkSelfPermission(context as Context,Manifest.permission.SEND_SMS)
                == PermissionChecker.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context as Context,Manifest.permission.ACCESS_FINE_LOCATION)
                        == PermissionChecker.PERMISSION_GRANTED)){
                sendSms()
            }
            else{
                if(ActivityCompat.checkSelfPermission(context as Context,
                        Manifest.permission.SEND_SMS
                    ) != PermissionChecker.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(context as Activity, arrayOf
                        (Manifest.permission.SEND_SMS),requestSendSms)
                }
                if(ActivityCompat.checkSelfPermission(context as Context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PermissionChecker.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(context as Activity, arrayOf
                        (Manifest.permission.ACCESS_FINE_LOCATION),requestGetPosition)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,grantResults: IntArray) {
        if (requestCode == requestSendSms && requestCode == requestGetPosition ) sendSms()
    }

    private fun sendSms() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val latitude  = location?.latitude;
                val langitude = location?.longitude;

                val number = "0766766021"
                val text = "C'est un message d'alerte je suis en danger " +
                        "http://www.google.com/maps/place/"+latitude+","+langitude

                SmsManager.getDefault().sendTextMessage(number,null,text,null,null)

                Toast.makeText(context as Context,"The Alert have been sending",Toast.LENGTH_SHORT).show()
            }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
