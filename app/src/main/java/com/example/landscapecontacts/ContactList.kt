package com.example.landscapecontacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ContactList : Fragment() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val vista: View =  inflater.inflate(R.layout.contact_list, container, false)

        val listaDeContactos: RecyclerView = vista.findViewById(R.id.recycler_contacts)


        listaDeContactos.setHasFixedSize(true)

        readData()

        // LLAAMAR A FUNCION PARA OBTENER DATOS DE FIREBASE, QUE CREE EL ARRAY CON LOS OBJETOS Contact.

        return vista
    }

    // CREAR FUNCION PARA OBTENER DATOS DESDE FIREBASE

    private fun readData() {


    }


}