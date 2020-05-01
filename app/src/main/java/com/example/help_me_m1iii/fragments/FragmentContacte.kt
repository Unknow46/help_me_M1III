package com.example.help_me_m1iii.fragments

import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.adapters.ContactAdapter
import com.example.help_me_m1iii.models.Contacte

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "Contacte"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentContacte : Fragment() {
    // TODO: Rename and change types of parameters
    protected lateinit var rootView: View
    private var contacte_list: MutableList<Contacte>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ContactAdapter

    companion object {
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
        adapter = ContactAdapter(users)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater?.inflate(R.layout.activity_contacts_list,
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
                                        User.add(Contacte(name))
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
