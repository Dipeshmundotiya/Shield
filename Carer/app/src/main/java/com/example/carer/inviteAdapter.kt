package com.example.carer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class inviteAdapter(private val listInvite: ArrayList<ContactModel>) :RecyclerView.Adapter<inviteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): inviteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.iteminvite,parent,false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return listInvite.size
    }

    override fun onBindViewHolder(holder: inviteAdapter.ViewHolder, position: Int) {
       val item = listInvite[position]
        holder.name.text = item.name

    }
    class ViewHolder(private val item :View) : RecyclerView.ViewHolder(item){
        val imageUser = item.findViewById<ImageView>(R.id.inviteuserimage)
        val name = item.findViewById<TextView>(R.id.inviteusername)

    }
}