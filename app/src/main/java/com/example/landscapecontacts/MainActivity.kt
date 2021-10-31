package com.example.landscapecontacts

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.landscapecontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var isActive: Boolean = false

    //Usamos el binding para hacer referencia al layout con la variable "view"
    //Despues la implementamos con el setOnClickListener
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val animatedBackground:AnimationDrawable = binding.backgroundScreen.background as AnimationDrawable
        animatedBackground.setEnterFadeDuration(2500)
        animatedBackground.setExitFadeDuration(2500)
        animatedBackground.start()

        //Inicio de actividad, se oculta el fragment (FrameLayout)
        deleteFrag()

        //Ejecutamos la funcion

        binding.btn?.setOnClickListener {
            if(binding.frag != null) {
                val frag = supportFragmentManager.findFragmentById(R.id.frag)
                val transaction = supportFragmentManager.beginTransaction()
                if (frag != null) {
                    transaction
                        .setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                        .remove(frag)
                        .commit()
                }
            }
            showFrag()
            isActive = true
        }

        //Cualquiera fuera la "view" sobre la cual se haga "click", cierra el fragment
        view.setOnClickListener {
            deleteFrag()
        }
    }

    //Muestra el fragment
    @SuppressLint("UseCompatLoadingForDrawables")
    fun showFrag(){
        val frag = supportFragmentManager.findFragmentById(R.id.frag_2)
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddContact()
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                .setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                .add(R.id.frag_2, fragment)
                .commit()
            binding.btn!!.foreground = getDrawable(R.drawable.back)
        } else{
            deleteFrag()
        }
    }

    //Borra el fragment
    @SuppressLint("UseCompatLoadingForDrawables")
    fun deleteFrag(){
        val frag = supportFragmentManager.findFragmentById(R.id.frag_2)
        val transaction = supportFragmentManager.beginTransaction()
        if (frag != null) {
            transaction
                .setCustomAnimations(R.anim.enter_from_above, R.anim.exit_to_above)
                .remove(frag)
                .commit()
            binding.btn!!.foreground = getDrawable(R.drawable.add_contact)
        }
    }
}