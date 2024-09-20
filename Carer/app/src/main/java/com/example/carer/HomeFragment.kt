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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private val listContacts: ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val listMember = listOf<ItemModel>(
            ItemModel("Dipesh"),
            ItemModel("Mundotiya"),
            ItemModel("Amandeep"),
            ItemModel("Sahil")
        )

        val adapter = ItemAdapter(listMember)
        val recycler = requireView().findViewById<RecyclerView>(R.id.itemRecyclerview)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val inviteadapter = inviteAdapter(listContacts)

        CoroutineScope(Dispatchers.IO).launch {
            listContacts.addAll(fetchContacts())

            withContext(Dispatchers.Main) {
                inviteadapter.notifyDataSetChanged()
            }
        }

        val recycler2 = requireView().findViewById<RecyclerView>(R.id.inviterecycler)
        recycler2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler2.adapter = inviteadapter
    }

    @SuppressLint("Range")
    private fun fetchContacts(): ArrayList<ContactModel> {
        val cr = requireActivity().contentResolver
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
                    // Query to fetch phone numbers
                    val pcur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    // Fetch only the first phone number
                    if (pcur != null && pcur.moveToFirst()) {
                        val phonenum =
                            pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        listContact.add(ContactModel(name, phonenum)) // Add contact with first phone number
                    }

                    // Close the phone number cursor
                    pcur?.close()
                }
            }
            // Close the main contact cursor after processing
            cursor.close()
        }

        return listContact
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
