package com.example.emergencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voltar = findViewById<ImageButton>(R.id.Back)
        voltar.setOnClickListener { finish() }

        val service = intent.getStringExtra("service")
        val infos = intent.getStringExtra("infos")
        val numero = service?.substring(0,3)

        findViewById<android.widget.TextView>(R.id.Service).text = service
        findViewById<android.widget.TextView>(R.id.Infos).text = infos

        val call = findViewById<Button>(R.id.Call)
        call.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = "tel:$numero".toUri()
            startActivity(dialIntent)
        }

    }

}