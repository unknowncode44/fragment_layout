package com.example.landscapecontacts

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainLayout: ConstraintLayout = findViewById(R.id.background_screen)

        val animatedBackground:AnimationDrawable = mainLayout.background as AnimationDrawable
        animatedBackground.setEnterFadeDuration(2500)
        animatedBackground.setExitFadeDuration(5000)
        animatedBackground.start()
    }
}