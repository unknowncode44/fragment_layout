package com.example.landscapecontacts

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddContact : Fragment() {


    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_contact, container, false)

        val addContactFragment = (R.id.frag_2)

        val image: ImageView = view.findViewById(R.id.image)
        val name: EditText = view.findViewById(R.id.name)
        val number: EditText = view.findViewById(R.id.number)
        val email: EditText = view.findViewById(R.id.email)
        val title: EditText = view.findViewById(R.id.title)
        val btn: Button = view.findViewById(R.id.btn)

        btn.setOnClickListener {

            if(name.text.isNotEmpty() && number.text.isNotEmpty() && email.text.isNotEmpty()
                && title.text.isNotEmpty()){
                val contact = hashMapOf(
                    "email" to email.text.toString(),
                    "name" to name.text.toString(),
                    "number" to number.text.toString(),
                    "title" to title.text.toString()
                )

                db.collection("contacts").document(number.text.toString())
                    .set(contact)
                    .addOnSuccessListener { Log.d(TAG, "Se agregaron los datos con exito") }
                    .addOnFailureListener{e -> Log.w(TAG, "Error en la carga", e)}

                Toast.makeText(context, "Nuevo contacto creado", Toast.LENGTH_LONG).show()
                deleteFrag(addContactFragment)
            }else{
                Toast.makeText(context, "Campos Obligatorios", Toast.LENGTH_LONG).show()
            }


        }


        return view
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