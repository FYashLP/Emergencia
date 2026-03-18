package com.example.emergencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pm = findViewById<Button>(R.id.PM) // pega o botão
        pm.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL) // abre o discador
            intent.data = "tel:190".toUri() // número preenchido
            startActivity(intent) // abre
        }

        val prf = findViewById<Button>(R.id.PRF)
        prf.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:191".toUri()
            startActivity(intent)
        }

        val pre = findViewById<Button>(R.id.PRE)
        pre.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:198".toUri()
            startActivity(intent)
        }

        val samu = findViewById<Button>(R.id.SAMU)
        samu.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:192".toUri()
            startActivity(intent)
        }

        val cb = findViewById<Button>(R.id.CB)
        cb.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:193".toUri()
            startActivity(intent)
        }

        val dc = findViewById<Button>(R.id.DC)
        dc.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:199".toUri()
            startActivity(intent)
        }

        val cam = findViewById<Button>(R.id.CAM)
        cam.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:180".toUri()
            startActivity(intent)
        }

        val ddh = findViewById<Button>(R.id.DDH)
        ddh.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:100".toUri()
            startActivity(intent)
        }

        val procon = findViewById<Button>(R.id.PROCON)
        procon.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:151".toUri()
            startActivity(intent)
        }

        val cvv = findViewById<Button>(R.id.CVV)
        cvv.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:141".toUri()
            startActivity(intent)
        }

    }
}