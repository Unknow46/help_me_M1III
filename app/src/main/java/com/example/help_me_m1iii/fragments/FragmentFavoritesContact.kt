package com.example.help_me_m1iii.fragments

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

/**
 * A simple [Fragment] subclass.
 */
class FragmentFavoritesContact : Fragment() {
    protected lateinit var rootView: View
    var contacte_list: MutableList<Contacte>? = null
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
        //this.contacte_list = getFavorite()
        onCreateComponent(this.contacte_list!!)
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

