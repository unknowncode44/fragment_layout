package com.example.landscapecontacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class ContactList : Fragment() {


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

        // LLAAMAR A FUNCION PARA OBTENER DATOS DE FIREBASE, QUE CREE EL ARRAY CON LOS OBJETOS Contact.

        return vista
    }

    // CREAR FUNCION PARA OBTENER DATOS DESDE FIREBASE


}