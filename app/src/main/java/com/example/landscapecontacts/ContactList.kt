package com.example.landscapecontacts

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ContactList : Fragment() {

    // VARIABLES DE TRABAJO
    var isActive: Boolean = false
    private var db = Firebase.firestore // creamos una variable de tipo firestore
    private lateinit var recyclerView: RecyclerView // variable para el recycler
    private lateinit var contactArrayList: ArrayList<Contact> // variable para el array
    private lateinit var adapter: ContactCardAdapter // otra para el adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // VARIABLE VISTA, INSTANCIAMOS EL LAYOUT CORRESPONDIENTE
        val vista: View =  inflater.inflate(R.layout.contact_list, container, false)



        // manejamos el recycler
        recyclerView = vista.findViewById(R.id.recycler_contacts) // primero lo instaciamos con la vista del recycler creado
        recyclerView.layoutManager = LinearLayoutManager(context) // le asignamos el manager
        recyclerView.setHasFixedSize(true) // decimos que tiene un tamano fijo


        contactArrayList = arrayListOf() // instanciamos el array
        adapter = ContactCardAdapter(contactArrayList) // y se lo pasamos al adapter
        recyclerView.adapter = adapter // instanciamos el adaptador que creamos

        // con esta funcion vamos mandar los items al otro fragment
        adapter.setOnItemClickListener(object : ContactCardAdapter.OnItemClickListener{
            // en el adaptador ya creamos un metodo que almacena los datos que necesitamos
            override fun onItemClick(position: Int, name: String, number: String, email: String, title: String, imageUrl: String)  {
                // usamos bundle para almacenar los datos en "cache" y asi poder usarlos en el otro fragment
                val bundle = Bundle()


                bundle.putString("number",number)
                bundle.putString("name",name)
                bundle.putString("email",email)
                bundle.putString("title",title)
                bundle.putString("imageUrl",imageUrl)

                val detailsFragment = ContactsDetails() // instacianciamos el otro fragment
                detailsFragment.arguments = bundle // le pasamos el bundle con los datos guardados
                val fragmentLayout: Int = (R.id.frag)
                val addContactFragment = (R.id.frag_2)
                val addContactIsActive: Boolean = MainActivity().isActive


                isActive = if (!addContactIsActive) {
                    deleteFrag(addContactFragment)
                    showFrag(detailsFragment, fragmentLayout)
                    true

                } else {
                    showFrag(detailsFragment, fragmentLayout)
                    true
                }


//                val fragmentTransaction = fragmentManager?.beginTransaction()
//                fragmentTransaction?.replace(R.id.frag, detailsFragment)
//                    ?.setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
//                    ?.commit()


            }


        })


        // EJECUTAMOS LA FUNCION DE LECTURA DE DATOS EN DATABASE
        readData()

        // RETORNAMOS VISTA
        return vista
    }

    //########################################################################---METODO DEL FRAGMENT---############################################################################//

    // CREAMOS FUNCION PARA OBTENER DATOS DESDE FIREBASE FIRESTORE
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
    //Muestra el fragment
    private fun showFrag(fragment: Fragment, fragLayOut: Int ){
        val frag = fragmentManager?.findFragmentById(fragLayOut)
        val transaction = fragmentManager?.beginTransaction()
        val currentFragment = fragment
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                ?.setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                ?.add(fragLayOut, currentFragment)
                ?.commit()
        } else{
            deleteFrag(fragLayOut)
            transaction
                ?.setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                ?.add(fragLayOut, currentFragment)
                ?.commit()

        }
    }

    //Borra el fragment
    private fun deleteFrag(fragLayOut: Int) {
        val frag = fragmentManager?.findFragmentById(fragLayOut)
        val transaction = fragmentManager?.beginTransaction()
        if (frag != null) {
            transaction
                ?.setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                ?.remove(frag)
                ?.commit()
        }
    }


}