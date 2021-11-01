package com.example.landscapecontacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ModifyContacts : Fragment() {

    private lateinit var name: EditText
    private lateinit var number: EditText
    private lateinit var email: EditText
    private lateinit var title: EditText
    private lateinit var btn: Button
    private lateinit var id: String
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_modify_contacts, container, false)

        name = vista.findViewById(R.id.name)
        number = vista.findViewById(R.id.number)
        email = vista.findViewById(R.id.email)
        title = vista.findViewById(R.id.title)

        // antes de inflar la vista le pasamos los datos que almacenamos en
        // el otro fragment, en el metodo onItemClick, para que nos muestre los datos del contacto que estamos seleccionando
        id = "${arguments?.getString("id")}"
        name.setText("${arguments?.getString("name")}")// nombre
        number.setText("${arguments?.getString("number")}") // numero
        email.setText("${arguments?.getString("email")}")// email
        title.setText("${arguments?.getString("title")}")// subtitulo

        btn = vista.findViewById(R.id.btn)
        btn.setOnClickListener {
            db.collection("contacts").document(id).set(
                hashMapOf(
                    "email" to email.text.toString(),
                    "number" to number.text.toString(),
                    "name" to name.text.toString(),
                    "title" to title.text.toString()
                )
            )
            val frag: Int = (R.id.frag_3)
            deleteFrag(frag)
        }
        return vista
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
