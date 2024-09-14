package com.example.carer

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import android.content.Context

import android.util.Log

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HomeFragment", "onCreateView called")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated called")

        val listMember = listOf(
            ItemModel("Dipesh"),
            ItemModel("Mundotiya"),
            ItemModel("Amandeep"),
            ItemModel("Sahil")
        )

        val adapter = ItemAdapter(listMember)
        val recycler = requireView().findViewById<RecyclerView>(R.id.itemRecyclerview)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // Check if recycler is set up correctly
        Log.d("HomeFragment", "RecyclerView 1 setup complete")

        // Fetch contacts in the background
        fetchContactsInBackground()
    }

    private fun fetchContactsInBackground() {
        CoroutineScope(Dispatchers.IO).launch {
            val contacts = fetchContacts(requireContext())

            // Check if contacts are fetched correctly
            Log.d("HomeFragment", "Contacts fetched: ${contacts.size}")

            // Once contacts are fetched, update the UI on the main thread
            withContext(Dispatchers.Main) {
                val inviteadapter = inviteAdapter(contacts)
                val recycler2 = requireView().findViewById<RecyclerView>(R.id.inviterecycler)
                recycler2.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                recycler2.adapter = inviteadapter

                // Check if second recycler view is set up correctly
                Log.d("HomeFragment", "RecyclerView 2 setup complete")
            }
        }
    }

    @SuppressLint("Range")
    private fun fetchContacts(context: Context): ArrayList<ContactModel> {
        val cr = context.contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null)

        val listContact: ArrayList<ContactModel> = ArrayList()

        if (cursor != null && cursor.count > 0) {

            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhoneNumber > 0) {
                    val pcur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    if (pcur != null) {
                        while (pcur.moveToNext()) {
                            val phonenum =
                                pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContact.add(ContactModel(name, phonenum))
                        }
                        pcur.close()
                    }
                }
            }
            cursor.close()
        }

        return listContact
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
