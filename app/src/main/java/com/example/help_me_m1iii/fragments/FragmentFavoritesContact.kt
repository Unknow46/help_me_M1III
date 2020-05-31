package com.example.help_me_m1iii.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.activities.Contacts
import com.example.help_me_m1iii.activities.FavoritesContact
import com.example.help_me_m1iii.adapters.FavoritesContactAdapter
import com.example.help_me_m1iii.models.Contacte
import java.io.*
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass.
 */
class FragmentFavoritesContact : Fragment() {
    protected lateinit var rootView: View
    val contactes: MutableList<Contacte> = ArrayList()
    private var contacte_list: MutableList<Contacte>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesContactAdapter


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

        if (contactes.isEmpty()){
            val intent = Intent(activity, Contacts::class.java)
            startActivity(intent)
            activity?.finish()
        }

        this.contacte_list = contactes
        onCreateComponent(this.contacte_list!!)
    }

    private fun retrieveContact() {
        var fileInputString: FileInputStream? = null
        val file: File? = context?.getFileStreamPath("save.txt")

        if (file?.exists()!!){
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
    }


    private fun onCreateComponent(users: MutableList<Contacte>) {
        adapter = FavoritesContactAdapter(users) { contacte ->  deleteFavoritesContacte(contacte)}
    }

    private fun deleteFavoritesContacte(contacte: Contacte) {
        val contacte_to_delete = contacte_list!!.indexOf(contacte)
        contacte_list!!.remove(contacte)
        saveFavorite()
        adapter.notifyItemRemoved(contacte_to_delete)
    }

    private fun saveFavorite(){
        val fileOutputStream: FileOutputStream

        try {
            fileOutputStream = context!!.openFileOutput("save.txt", Context.MODE_PRIVATE)
            val nextLine = "\n"
            val escape = " "
            contacte_list?.forEach{
                fileOutputStream.write(it.name.toByteArray() + escape.toByteArray() + it.phone_number.toByteArray() + nextLine.toByteArray())
            }
        }catch (e: NumberFormatException){
            e.printStackTrace()
        }
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

    fun addingNewFavorites(){

        if (contacte_list?.size == 5){
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Erreur d'ajout")
            dialogBuilder.setMessage("Impossible d'ajouter plus de contactes")
            dialogBuilder.show()
        }else{
            val intent = Intent(activity, Contacts::class.java)
            startActivity(intent)
        }
    }


}

