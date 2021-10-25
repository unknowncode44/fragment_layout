package com.example.landscapecontacts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class ContactsDetails : Fragment() {

    // establecemos las variables de trabajo
    private lateinit var name: TextView
    private lateinit var number: TextView
    private lateinit var email: TextView
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var imageUrl: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // creamos variable vista, instanciamos la vista del contact_details
        val vista: View = inflater.inflate(R.layout.contacts_details, container, false)

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

        // una vez que ya tenemos los valores instanciados en sus respectivas variables, solo resta inflar la vista
        return vista


    }



}