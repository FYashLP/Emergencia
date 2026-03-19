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

        val pm = findViewById<Button>(R.id.PM)
        pm.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PM))
            intent.putExtra("infos", "Para crimes como;\n\n- Crimes em andamento\n- Violência domestica\n- Pertubação de ordem publica\n- Ameaças e intimidações\n- Acidentes em vias urbanas") ///sera que tem como bota o embed do decibel aqui??
            startActivity(intent)
        }

        val prf = findViewById<Button>(R.id.PRF)
        prf.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PRF))
            intent.putExtra("infos", "Para situações como;\n\n- Acidentes em rodovias federais (Ex; BR-116/BR-230/BR-101 etc...) \n- Veículos cometendo Infrações ou em estado irregular em trânsito\n- Animais em pista ou análogos")
            startActivity(intent)
        }

        val pre = findViewById<Button>(R.id.PRE)
        pre.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PRE))
            intent.putExtra("infos", "Para situações como;\n\n- Acidentes em rodovias estaduais (Ex; CE-060/CE-187/SP-330 etc...) \n- Veículos cometendo Infrações ou em estado irregular em trânsito\n- Animais em pista ou análogos")
            startActivity(intent)
        }

        val samu = findViewById<Button>(R.id.SAMU)
        samu.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.SAMU))
            intent.putExtra("infos", "Para ocorrências como;\n\n- Acidentes domésticos graves\n- Ocorrência e suspeita de ataques cardiovasculares\n- Acidentes de trânsito com vítimas\n- Intoxicações e envenenamento por produtos ou animais\n- Trabalho de parto em risco")
            startActivity(intent)
        }

        val cb = findViewById<Button>(R.id.CB)
        cb.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CB))
            intent.putExtra("infos", "Para situações como;\n\n- Incêndios fora de controle\n- Desabamento de estruturas\n- Resgates em locais de dificil acesso/ferragens/escombros\n- Vazamento de gases")
            startActivity(intent)
        }

        val dc = findViewById<Button>(R.id.DC)
        dc.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.DC))
            intent.putExtra("infos", "Para desastres como;\n\n- Enchentes e alagamentos\n- Deslizamentos de terra\n- Vendavais, tempestades e granizo intenso\n- Estruturas em estado instável")
            startActivity(intent)
        }

        val cam = findViewById<Button>(R.id.CAM)
        cam.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CAM))
            intent.putExtra("infos", "Para situações e indicios de;\n\n- Violência física ou psicológica\n- Ameaças, perseguição e importunação\n- Feminicídio em andamento\n- Denúncia anônima de agressão\n- também é recomendado denunciar para a PM nos mesmos casos")
            startActivity(intent)
        }

        val ddh = findViewById<Button>(R.id.DDH)
        ddh.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.DDH))
            intent.putExtra("infos", "Para casos de;\n\n- Abuso de autoridade\n- Discriminação racial, social ou de gênero\n- Exploração de trabalho infantil\n- Condições de trabalho escravo\n- Maus tratos a crianças, adolescentes, deficientes e idosos")
            startActivity(intent)
        }

        val procon = findViewById<Button>(R.id.PROCON)
        procon.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PROCON))
            intent.putExtra("infos", "Para casos de;\n\n- Fraudes em compras e vendas\n- Cobranças indevidas e abusivas\n- Propaganda enganosa\n- Recusa de atendimento e métodos de pagamento")
            startActivity(intent)
        }

        val cvv = findViewById<Button>(R.id.CVV)
        cvv.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CVV))
            intent.putExtra("infos", "Para situações como;\n\n- Pensamentos e crise suicida\n- Sofrimento emocional intenso\n- Apoio em momentos de desespero\n- O serviço conta com escuta anônima e sigilosa")
            startActivity(intent)
        }

    }
}