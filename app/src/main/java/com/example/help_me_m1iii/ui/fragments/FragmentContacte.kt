package com.example.help_me_m1iii.ui.fragments

import android.Manifest
import android.annotation.SuppressLint

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R


import com.example.help_me_m1iii.ui.adapters.ContactAdapter
import com.example.help_me_m1iii.ui.models.Contacte
import java.io.*
import java.lang.NumberFormatException


class FragmentContacte : Fragment() {

    // TODO: Rename and change types of parameters
    protected lateinit var rootView: View
    private var contacte_list: MutableList<Contacte>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ContactAdapter
    var favorite_Contact: MutableList<Contacte> = ArrayList()
    private lateinit var limiteUser: TextView
    private lateinit var selectedContact: TextView
    private lateinit var FavoritesContact: FragmentFavoritesContact

    companion object {
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
        var TAG = FragmentContacte::class.java.simpleName
        const val ARG_POSITION: String = "position"

        fun newInstance(): FragmentContacte {
            var fragment =
                FragmentContacte();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val check_permission = checkPermission()

        if(!check_permission){
            FavoritesContact = FragmentFavoritesContact()
            val refusedAccess = this.fragmentManager!!.beginTransaction()
            refusedAccess.replace(R.id.fragmentContainer, FavoritesContact)
            refusedAccess.commit()
        }

        val file: File? = context?.getFileStreamPath("save.txt")
        if(file?.exists()!!){
            favorites_reading()
            contacte_list = generateContacte()
            onCreateComponent(this.contacte_list!!)
        }else{
            contacte_list = generateContacte()
            onCreateComponent(this.contacte_list!!)
        }
    }

    private fun favorites_reading() {
        var fileInputString: FileInputStream
        favorite_Contact.clear()
        fileInputString = context!!.openFileInput("save.txt")
        var inputStreamReader = InputStreamReader(fileInputString)

        var bufferedReader = BufferedReader(inputStreamReader)

        var result: String? = null
        while ({ result = bufferedReader.readLine(); result} () !=  null) {
            //splitting the result to identify the contact
            val line:MutableList<String> = result?.split(" ") as MutableList<String>
            //get the phone number of each contact
            val phone_number = line.last()
            var contact_saved = ""
            line.remove(phone_number)
            line.forEach {
                contact_saved += "$it"
            }
            favorite_Contact.add(Contacte(contact_saved, phone_number))
        }
    }

    private fun onCreateComponent(users: MutableList<Contacte>) {
        adapter = ContactAdapter(users) { contacte ->  contacteClickListener(contacte)}
    }

    private fun contacteClickListener(contacte: Contacte) {

            if (favorite_Contact.size == 0) {
                favorite_Contact.add(contacte)
                showFavoriteContact(favorite_Contact)
            } else if (favorite_Contact.contains(contacte)) {
                favorite_Contact.remove(contacte)
                showFavoriteContact(favorite_Contact)
            } else {
                favorite_Contact.add(contacte)
                showFavoriteContact(favorite_Contact)
            }
        if (favorite_Contact.size == 5) {
            startFavoriteActivity()
        }
    }

    private fun startFavoriteActivity() {
        saveContact()
        FavoritesContact = FragmentFavoritesContact()
        val favContactTrans = this.fragmentManager!!.beginTransaction()
        favContactTrans.replace(R.id.fragmentContainer, FavoritesContact)
        favContactTrans.commit()
    }

    private fun saveContact() {
        val fileOutputStream: FileOutputStream

        try {
                fileOutputStream = context!!.openFileOutput("save.txt", Context.MODE_PRIVATE)
                val nextLine = "\n"
                val escape = " "
                favorite_Contact.forEach{
                    fileOutputStream.write(it.name.toByteArray() + escape.toByteArray() + it.phone_number.toByteArray() + nextLine.toByteArray())
                }
            }catch (e: NumberFormatException){
            e.printStackTrace()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showFavoriteContact(contactes: MutableList<Contacte>){
            var all_contacte = ""
            contactes.forEach { info ->
                all_contacte += info.name + "  "
            }

          limiteUser.text = contactes.size.toString() + "/5"
          selectedContact.text = all_contacte
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.activity_contacte,
            container, false)
        initRecycleView()
        limiteUser = rootView.findViewById(R.id.limite)
        selectedContact = rootView.findViewById(R.id.selected_contact)
        return rootView
    }

    fun initRecycleView(){
        recyclerView = rootView.findViewById(R.id.liste_contacte)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun generateContacte(): MutableList<Contacte> {
        val resolver: ContentResolver? = activity?.contentResolver
        val User: MutableList<Contacte> = ArrayList()
        val cursor = resolver?.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                            val phoneNumber = (cursor.getString(
                                cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                            if (phoneNumber > 0) {
                                val cursorPhone = resolver?.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                                if(cursorPhone.count > 0) {
                                    while (cursorPhone.moveToNext()) {
                                        val name = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                                        val phone_number = cursorPhone.getString(
                                            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                        User.add(Contacte(name, phone_number))
                                    }

                                }
                                cursorPhone.close()
                            }
                        }
            } else {
                //   tost("No contacts available!")
            }
        }
        cursor?.close()
        return User
    }


    private fun checkPermission(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context?.let {
                ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.READ_CONTACTS
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            //callback onRequestPermissionsResult
        } else {
            return true
        }
        return false
    }

    @Override
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showFavoriteContact(favorite_Contact)
    }

}
