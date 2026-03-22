package com.example.emergencia

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_config)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão de voltar, agindo fechando a view de configuração
        val voltar = findViewById<ImageButton>(R.id.Back)
        voltar.setOnClickListener { finish() }

        // val para guarda valor da preference
        val prefs = getSharedPreferences("config", MODE_PRIVATE)

        // Uso do spinner para não ter que abrir uma outra view so para mudar de tema
        val spinnerThemes = findViewById<Spinner>(R.id.ThemesDD) // Confundindo DropDown com spinner e ficou isso mesmo
        val themes = arrayOf("Auto", "Claro", "Escuro") // Auto para sistema, claro pra modo de dia, e escuro para modo noturno
        spinnerThemes.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, themes) // Adaptando o array themes para as opções do spinner
        spinnerThemes.setSelection(prefs.getInt("tema_index", 0))

        var setTheme = true // "trava" para ignorar a primeira escolha quando o spinner é criado
        spinnerThemes.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: android.widget.AdapterView<*>?, p1: android.view.View?, pos: Int, p3: Long) {
                if (setTheme) { setTheme = false; return }
                prefs.edit().putInt("tema_index", pos).apply() // Overwrite na prefs (val de sharedprefs)
                AppCompatDelegate.setDefaultNightMode(when (pos) { // Setando o modo mediante do estado do modo noturno
                    1 -> AppCompatDelegate.MODE_NIGHT_NO // Modo noturno off (modo dia)
                    2 -> AppCompatDelegate.MODE_NIGHT_YES // Modo noturno on
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // De acordo com o sistema (horario do sistema no caso)
                })
            }
            override fun onNothingSelected(p0: android.widget.AdapterView<*>?) {}
        }

    }

}