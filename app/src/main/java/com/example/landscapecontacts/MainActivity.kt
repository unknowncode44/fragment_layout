package com.example.landscapecontacts

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.landscapecontacts.databinding.ActivityMainBinding


//--------------------->COSAS QUE SE DEBEN PULIR PARA LA PROXIMA: <---------------------------//
            //Pasar la posicion del objeto de lista

//Creamos la extencion del interface para comunicar los fragment
open class MainActivity : AppCompatActivity(), Communicator {
    var isActive: Boolean = false

    //Usamos el binding para hacer referencia al layout con la variable "view"
    //Despues la implementamos con el setOnClickListener
    lateinit var binding: ActivityMainBinding
    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Comparamos la orientacion, caso contrario se cierra la app porque le llegan valores nulos
        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            val fragment = ContactList()
            showFrag(supportFragmentManager, fragment, R.id.frag_1)
            binding.btn?.foreground = getDrawable(R.drawable.add_contact)
        }



        //Animacion de fondo
        val animatedBackground: AnimationDrawable = binding.backgroundScreen.background as AnimationDrawable
        animatedBackground.setEnterFadeDuration(2500)
        animatedBackground.setExitFadeDuration(2500)
        animatedBackground.start()

        //Ejecutamos la funcion
        binding.btn?.setOnClickListener {
            if(binding.frag != null) {
                deleteFrag(supportFragmentManager, R.id.frag)
            }
            val addContact = AddContact()
            showFrag(supportFragmentManager, addContact, R.id.frag_3)
            isActive = true
        }

        //Cualquiera fuera la "view" sobre la cual se haga "click", cierra el fragment
        view.setOnClickListener {
            deleteFrag(supportFragmentManager, R.id.frag_3)
        }
    }

    //Para que el metodo funcione en los fragment, tenemos que pasar el tipo de
    //fragment manager (el Main usa uno diferente)

    //Muestra los fragment
    @SuppressLint("UseCompatLoadingForDrawables")
    fun showFrag(support: FragmentManager, fragment: Fragment, frame: Int){
        val frag = support.findFragmentById(frame)
        val transaction = support.beginTransaction()
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                .setCustomAnimations(R.anim.enter_from_above, R.anim.enter_from_above)
                .add(frame, fragment)
                .commit()
        } else{
            deleteFrag(supportFragmentManager, frame)
        }
    }

    //Borra el fragment
    @SuppressLint("UseCompatLoadingForDrawables")
    fun deleteFrag(support: FragmentManager, frame: Int){
        val frag = support.findFragmentById(frame)
        val transaction = support.beginTransaction()
        if (frag != null) {
            transaction
                .setCustomAnimations(R.anim.exit_to_above, R.anim.exit_to_above)
                .remove(frag)
                .commit()
        }
    }

    //Sobreescribimos la funcion del interface
    override fun restartFrag(boolean: Boolean) {
        if (boolean) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction
                .replace(R.id.frag_1, ContactList())
                .commit()
        }
    }
}