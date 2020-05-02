package com.example.help_me_m1iii.fragments

import android.Manifest
import android.annotation.SuppressLint

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.renderscript.RenderScript
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.activities.Contacts

import com.example.help_me_m1iii.activities.FavoritesContact
import com.example.help_me_m1iii.adapters.ContactAdapter
import com.example.help_me_m1iii.models.Contacte
import kotlinx.android.synthetic.main.activity_contacte.*
import java.io.FileOutputStream
import java.lang.NumberFormatException


class FragmentContacte : Fragment() {

    // TODO: Rename and change types of parameters
    protected lateinit var rootView: View
    private var contacte_list: MutableList<Contacte>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ContactAdapter
    var favorite_Contact: MutableList<Contacte> = ArrayList()

    companion object {
        const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_FILES = 100
        var TAG = FragmentContacte::class.java.simpleName
        const val ARG_POSITION: String = "positioin"

        fun newInstance(): FragmentContacte {
            var fragment = FragmentContacte();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contacte_list = generateContacte()
        onCreateComponent(this.contacte_list!!)
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
        val intent = Intent(activity, FavoritesContact::class.java)
        startActivity(intent)
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
        contactes.forEach {info ->
            all_contacte += info.name + "  "
        }
        activity!!.limite.text = contactes.size.toString()+"/5"
        activity!!.selected_contact.text = all_contacte
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.activity_contacts_list,
            container, false)
        initRecycleView()
        return rootView
    }

    fun initRecycleView(){
        recyclerView = rootView.findViewById(R.id.listContacts)
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

}
