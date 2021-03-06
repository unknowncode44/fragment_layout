package com.example.landscapecontacts


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ContactsDetails : Fragment() {

    // establecemos las variables de trabajo
    private lateinit var name: TextView
    private lateinit var number: TextView
    private lateinit var email: TextView
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var imageUrl: String
    private val db = Firebase.firestore
    //Instanciamos el interface encargado de comunicar los fragment y el MainActivity
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // creamos variable vista, instanciamos la vista del contact_details
        val vista: View = inflater.inflate(R.layout.contacts_details, container, false)

        //Communicator
        communicator = activity as Communicator

        // instanciamos los componentes visuales del layout a traves de sus ids
        image = vista.findViewById(R.id.detail_image) // imagen
        name = vista.findViewById(R.id.detail_name) // texto nombre
        number = vista.findViewById(R.id.detail_number) // numero de telefono
        email = vista.findViewById(R.id.detail_email) // email
        title = vista.findViewById(R.id.detail_title) // subtitulo
        imageUrl = "${arguments?.getString("imageUrl")}" // url de la imagen, luego la instanciaremos usando Glide.

        // antes de inflar la vista le pasamos los datos que almacenamos en
        // el otro fragment, en el metodo onItemClick, para que nos muestre los datos del contacto que estamos seleccionando
        name.text = "${arguments?.getString("name")}" // nombre
        number.text = "${arguments?.getString("number")}" // numero
        email.text = "${arguments?.getString("email")}" // email
        title.text = "${arguments?.getString("title")}" // subtitulo
        Glide.with(this).load(imageUrl).into(image) // aca desplegamos la imagen desde la url que almacenamos en la db

        val btn: ImageButton = vista.findViewById(R.id.modify)
        val fragmentLayout: Int = (R.id.frag_3)
        val fragLayOut2: Int = (R.id.frag)

        //Modificar el contacto
        btn.setOnClickListener {
            // usamos bundle para almacenar los datos en "cache" y asi poder usarlos en el otro fragment
            val bundle = Bundle()

            bundle.putString("number",number.text.toString())
            bundle.putString("name",name.text.toString())
            bundle.putString("email",email.text.toString())
            bundle.putString("title",title.text.toString())
            bundle.putString("imageUrl",imageUrl)

            val modifyFragment = ModifyContacts() // instacianciamos el otro fragment
            modifyFragment.arguments = bundle // le pasamos el bundle con los datos guardados
            showFrag(modifyFragment, fragmentLayout)
            deleteFrag(fragLayOut2)
        }

        //Borramos el contacto
        val btnDelete: ImageButton = vista.findViewById(R.id.delete)
        btnDelete.setOnClickListener {
            showDialogAlertSimple(number.text.toString(), fragLayOut2, communicator)
        }

        // una vez que ya tenemos los valores instanciados en sus respectivas variables, solo resta inflar la vista
        return vista

    }

    //Muestra el fragment
    private fun showFrag(fragment: Fragment, fragLayOut: Int ){
        val frag = fragmentManager?.findFragmentById(fragLayOut)
        val transaction = fragmentManager?.beginTransaction()
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                ?.setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                ?.add(fragLayOut, fragment)
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

    //Mostramos el dialogo
    fun showDialogAlertSimple(number: String, fragLayOut: Int, communicator: Communicator) {
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(R.string.delete_contact))
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    db.collection("contacts").document(number).delete()
                    deleteFrag(fragLayOut)
                    communicator.restartFrag(true)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                })
            //Con esta opcion, quitamos la opcion de tocar en la pantalla gris y que se cierre
            //Si el valor es false, no se cierra, con true si. true viene por defecto
            .setCancelable(true)
            .show()
    }
}