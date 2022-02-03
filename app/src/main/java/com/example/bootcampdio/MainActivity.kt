package com.example.bootcampdio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btEvent : Button
    private lateinit var btcontacts : Button
    private lateinit var btpicture : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btEvent = findViewById(R.id.btEvent)
        btcontacts = findViewById(R.id.btContacts)
        btpicture = findViewById(R.id.btpictures)
        btEvent.setOnClickListener {
            var intent = Intent(this, MarcarEvento::class.java)
            startActivity(intent)
        }
        btcontacts.setOnClickListener {
            var intent = Intent(this, ContatosActivity::class.java)
            startActivity(intent)
        }
        btpicture.setOnClickListener {
            var intent = Intent(this, PictureActivity::class.java)
            startActivity(intent)
        }
    }

}