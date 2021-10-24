package com.example.landscapecontacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ContactList : Fragment() {

    private var db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactArrayList: ArrayList<Contact>
    private lateinit var adapter: ContactCardAdapter
    private lateinit var bundle: Bundle
    private lateinit var contactDetailsFragment: ContactsDetails



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val vista: View =  inflater.inflate(R.layout.contact_list, container, false)
        recyclerView = vista.findViewById(R.id.recycler_contacts)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        contactArrayList = arrayListOf()

        adapter = ContactCardAdapter(contactArrayList)

        recyclerView.adapter = adapter

        // con esta funcion deberiamos mandar los items al otro fragment

        adapter.setOnItemClickListener(object : ContactCardAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, name: String, number: String, email: String) {

                // TODO: 24/10/21 MANDAR LOS DATOS AL OTRO FRAGMENT, YA LOS TENEMOS!
                bundle.putString("identifier", number)
                contactDetailsFragment.arguments = bundle

//                val frTransaction = fragmentManager?.beginTransaction()
//                //seguir aca



                Toast.makeText(context, "hiciste click en $position nombre $name, numero $number, email $email", Toast.LENGTH_LONG).show()
            }

        })




        readData()


        return vista
    }

    // CREAR FUNCION PARA OBTENER DATOS DESDE FIREBASE
    private fun readData() {
        // primero obtenemos la instancia de la db
        db = FirebaseFirestore.getInstance()
        // luego apuntamos a la coleccion contacts donde estan guardados los contactos
        db.collection("contacts").
            addSnapshotListener(object : EventListener<QuerySnapshot>{ // creamos un listener para captar los nuevos contactos que agreguemos (tambien aplica para los que ya tenemos)
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(
                    value: QuerySnapshot?, // value sera igual al snapshot que hagamos
                    error: FirebaseFirestoreException? // value sera igual al error que detecte
                ) {
                    if (error != null) { // si error es distinto de nulo, es decir que existe el error, lo mostraremos por consola
                        Log.e("Error!!", error.message.toString())
                        return // hacemos return void
                    }
                    // si no hay error hacemos un loop para agregar los datos al array
                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            Log.d("DATABASE","SE ENCONTRARON DATOS!!")
                            contactArrayList.add(dc.document.toObject(Contact::class.java)) //lo agregamos usando la clase que creamos para los contacts
                        }
                    }
                    adapter.notifyDataSetChanged() // notificamos al adaptador para que cree las tarjetas
                }
            })

    }


}