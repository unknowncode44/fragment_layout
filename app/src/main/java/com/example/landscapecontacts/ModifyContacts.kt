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
import kotlinx.android.synthetic.main.fragment_add_contact.*


class ModifyContacts : Fragment() {

    private lateinit var name: EditText
    private lateinit var number: EditText
    private lateinit var email: EditText
    private lateinit var title: EditText
    private lateinit var btn: Button
    private lateinit var id: String
    private val db = Firebase.firestore
    //Instanciamos el interface encargado de comunicar los fragment y el MainActivity
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_modify_contacts, container, false)

        val main = MainActivity()

        //Communicator
        communicator = activity as Communicator

        val btnCancel: Button = vista.findViewById(R.id.btn_cancel)

        name = vista.findViewById(R.id.name)
        number = vista.findViewById(R.id.number)
        email = vista.findViewById(R.id.email)
        title = vista.findViewById(R.id.title)

        // antes de inflar la vista le pasamos los datos que almacenamos en
        // el otro fragment, en el metodo onItemClick, para que nos muestre los datos del contacto que estamos seleccionando
        name.setText("${arguments?.getString("name")}")// nombre
        number.setText("${arguments?.getString("number")}") // numero
        email.setText("${arguments?.getString("email")}")// email
        title.setText("${arguments?.getString("title")}")// subtitulo

        //Modificar el contacto
        //Para ello, borramos la entrada vieja, y agregamos la nueva
        btn = vista.findViewById(R.id.modify)
        btn.setOnClickListener {
            db.collection("contacts").document(number.text.toString()).delete()
            db.collection("contacts").document(number.text.toString()).set(
                hashMapOf(
                    "email" to email.text.toString(),
                    "number" to number.text.toString(),
                    "name" to name.text.toString(),
                    "title" to title.text.toString()
                )
            )
            val frag: Int = (R.id.frag_3)
            main.deleteFrag(fragmentManager!!, frag)

            //Mandamos el comunicador para reiniciar el recyclerView
            communicator.restartFrag(true)

        }

        //Boton de cancelar, borra el fragment
        btnCancel.setOnClickListener {
            main.deleteFrag(fragmentManager!!, R.id.frag_3)
        }

        return vista
    }
}
