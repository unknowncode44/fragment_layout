package com.example.landscapecontacts

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.landscapecontacts.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var isActive: Boolean = false

    //Usamos el binding para hacer referencia al layout con la variable "view"
    //Despues la implementamos con el setOnClickListener
    lateinit var binding: ActivityMainBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Declaramos el boton
        val btn: FloatingActionButton = findViewById(R.id.btn)

        //Animacion de fondo
        val animatedBackground:AnimationDrawable = binding.backgroundScreen.background as AnimationDrawable
        animatedBackground.setEnterFadeDuration(2500)
        animatedBackground.setExitFadeDuration(2500)
        animatedBackground.start()

        val fragment = ContactList()
        showFrag(btn, fragment, R.id.frag_1)
        btn.foreground = getDrawable(R.drawable.add_contact)

        //Ejecutamos la funcion
        btn.setOnClickListener {
            if(binding.frag != null) {
                deleteFrag(btn, R.id.frag)
            }
            val addContact = AddContact()
            showFrag(btn, addContact, R.id.frag_3)
            isActive = true
        }

        //Cualquiera fuera la "view" sobre la cual se haga "click", cierra el fragment
        view.setOnClickListener {
            deleteFrag(btn, R.id.frag_3)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showFrag(btn: FloatingActionButton, fragment: Fragment, frame: Int){
        val frag = supportFragmentManager.findFragmentById(frame)
        val transaction = supportFragmentManager.beginTransaction()
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                .setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                .add(frame, fragment)
                .commit()
            btn.foreground = getDrawable(R.drawable.back)
        } else{
            deleteFrag(btn, frame)
        }
    }

    //Borra el fragment
    @SuppressLint("UseCompatLoadingForDrawables")
    fun deleteFrag(btn: FloatingActionButton, frame: Int){
        val frag = supportFragmentManager.findFragmentById(frame)
        val transaction = supportFragmentManager.beginTransaction()
        if (frag != null) {
            transaction
                .setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                .remove(frag)
                .commit()
            btn.foreground = getDrawable(R.drawable.add_contact)
        }
    }
}