package com.example.landscapecontacts

import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


class ContactCardAdapter(private val contactList: ArrayList<Contact>): RecyclerView.Adapter<ContactCardAdapter.ContactCardViewHolder>() {

    // creamos este listener para escuchar luego si hacen un click en uno de los contactos, de esa manera recogeremos la data y la mandaremos al otro fragment
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int, name: String, number: String, email: String, title: String, imageUrl: String) // definimos las variables que obtendremos al tocar el boton
    }

    // creamos la funcion para poder usarla luego en el fragment
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
        return ContactCardViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ContactCardViewHolder, position: Int) {
        val contact: Contact = contactList[position]


        holder.name.text = contact.name
        holder.number.text = contact.number
        holder.email.text = contact.email
        holder.title.text = contact.title
        holder.imageUrl.text = contact.image

    }




    override fun getItemCount(): Int {
        return contactList.size
    }

    class ContactCardViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) { // agregamos el listener a los parametros
        val name: TextView = itemView.findViewById(R.id.contact_name)
        val number: TextView = itemView.findViewById(R.id.contact_number)
        val email: TextView = itemView.findViewById(R.id.contact_email)
        val title: TextView = itemView.findViewById(R.id.contact_title)
        val imageUrl: TextView = itemView.findViewById(R.id.contact_image_url)



        // le pasamos los datos que recogimos al listener para tenerlos disponibles para pasarlos al otro fragment

        init {
            itemView.setOnClickListener {

                listener.onItemClick(bindingAdapterPosition, name.text.toString(), number.text.toString(), email.text.toString(), title.text.toString(), imageUrl.text.toString())
            }

        }

    }
}