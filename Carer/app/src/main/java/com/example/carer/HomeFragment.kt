package com.example.carer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {


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
        ItemModel("Sahil"))

        val adapter = ItemAdapter(listMember)
        val recycler = requireView().findViewById<RecyclerView>(R.id.itemRecyclerview)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }
    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
            }
    }
