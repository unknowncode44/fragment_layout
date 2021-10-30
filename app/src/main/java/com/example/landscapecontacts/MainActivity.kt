package com.example.landscapecontacts

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.landscapecontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val mainLayout: ConstraintLayout = findViewById(R.id.background_screen)

        val animatedBackground:AnimationDrawable = mainLayout.background as AnimationDrawable
        animatedBackground.setEnterFadeDuration(2500)
        animatedBackground.setExitFadeDuration(2500)
        animatedBackground.start()

        //Inicio de actividad, se oculta el fragment (FrameLayout)
        deleteFrag()

        //Llamamos al boton
        val btn: Button = findViewById(R.id.btn)

        //Ejecutamos la funcion
        btn.setOnClickListener {
            showFrag()
        }

        //Cualquiera fuera la "view" sobre la cual se haga "click", cierra el fragment
        view.setOnClickListener {
            deleteFrag()
        }
    }

    //Muestra el fragment
    fun showFrag(){
        val frag = supportFragmentManager.findFragmentById(R.id.frag_2)
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddContact()
        //Esta condicion, sino se muestra una y otra vez el fragment, uno arriba del otro
        if(frag == null){
            transaction
                .setCustomAnimations(R.anim.up_to_down, R.anim.center_to_down)
                .add(R.id.frag_2, fragment)
                .commit()
        }
    }

    //Borra el fragment
    fun deleteFrag(){
        val frag = supportFragmentManager.findFragmentById(R.id.frag_2)
        val transaction = supportFragmentManager.beginTransaction()
        if (frag != null) {
            transaction
                .remove(frag)
                .commit()
        }
    }
}