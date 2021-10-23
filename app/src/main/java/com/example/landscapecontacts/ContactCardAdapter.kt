package com.example.landscapecontacts

import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class ContactCardAdapter(private val contactList: ArrayList<Contact>): RecyclerView.Adapter<ContactCardAdapter.ContactCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        return ContactCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactCardViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.contactName.text = currentItem.name
        holder.contactNumber.text = currentItem.number
        holder.contactEmail.text = currentItem.email
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val contactNumber: TextView = itemView.findViewById(R.id.contact_number)
        val contactEmail: TextView = itemView.findViewById(R.id.contact_email)

    }
}