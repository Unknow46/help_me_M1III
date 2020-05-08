package com.example.help_me_m1iii.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.adapters.ContactAdapter
import com.example.help_me_m1iii.models.Contacte
import java.io.BufferedReader
import java.io.Console
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class FragmentFavoritesContact : Fragment() {
    protected lateinit var rootView: View
    val contactes: MutableList<Contacte> = ArrayList()
    private var contacte_list: MutableList<Contacte>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ContactAdapter


    companion object {
        var TAG = FragmentFavoritesContact::class.java.simpleName
        const val ARG_POSITION: String = "position"

        fun newInstance(): FragmentFavoritesContact {
            var fragment = FragmentFavoritesContact();
            val args = Bundle()
            args.putInt(ARG_POSITION, 1)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrieveContact()
        this.contacte_list = contactes
        onCreateComponent(this.contacte_list!!)
    }

    private fun retrieveContact() {
        var fileInputString: FileInputStream? = null
        fileInputString = context?.openFileInput("save.txt")
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
                contact_saved += "$it "
            }
            contactes.add(Contacte(contact_saved, phone_number))
        }

    }

    /**
    private fun getFavorite(): MutableList<Contacte>? {
        //TODO
    }**/

    private fun onCreateComponent(users: MutableList<Contacte>) {
        adapter = ContactAdapter(users) { contacte ->  contacteClickListener(contacte)}
    }

    private fun contacteClickListener(contacte: Contacte) {
        Log.d("Contacte :", contacte.name)
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

    private fun initRecycleView() {
        recyclerView = rootView.findViewById(R.id.listContacts)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}

