package com.example.emergencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Bloco responsável em settar o tema escolhido nas configurações na criação da view principal
        val prefs = getSharedPreferences("config", MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(when (prefs.getInt("tema_index", 0)) {
            1 -> AppCompatDelegate.MODE_NIGHT_NO // Modo diurno
            2 -> AppCompatDelegate.MODE_NIGHT_YES // Modo Noturno
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // Modo auto mediante do horário do dispositivo
        })

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bloco responsável por startar e encaminhar a view de configurações
        val config = findViewById<ImageButton>(R.id.Config)
        config.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java) // Direcionamento a view de configuração
            startActivity(intent) // Roda a view de configuração
        }

        // Bloco para starta e encaminhar para a view de detalhes de serviço, aproveitando para repassar strings especificas a cada botão
        val pm = findViewById<Button>(R.id.PM) // Linkando o botão a val a ser usada no listener
        pm.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java) // Direcionamento a view de detalhes
            intent.putExtra("service", getString(R.string.PM)) // Manda para a view de destino do intent, no caso 3 sets de strings com tags diferentes, uma maneira de ter uma activity padronizada em vez de 10 views diferente para cada função
            intent.putExtra("infos1", "- Crimes em andamento\n- Violência doméstica\n- Perturbação da ordem pública\n- Ameaças e intimidações\n- Acidentes em vias urbanas")
            intent.putExtra("infos2", "- Localização exata\n- O que está acontecendo\n- Se há feridos ou armas\n- Descrição de suspeito(s)\n\nNão desligue até o atendente liberar\nMantenha a calma e fale claramente")
            startActivity(intent) // Roda a view de detalhes
        }

        // basicamente todos os 9 blocos seguintes seguem a mesma lógica, denovo, para não ter varias views para a mesma coisa

        val prf = findViewById<Button>(R.id.PRF)
        prf.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PRF))
            intent.putExtra("infos1", "- Acidentes em rodovias federais (BR-116, BR-230, etc)\n- Infrações graves em rodovias federais\n- Veículos suspeitos ou irregulares\n- Animais na pista")
            intent.putExtra("infos2", "- Nome da rodovia e KM aproximado\n- Sentido (ex: sentido Fortaleza)\n- Número de veículos e feridos\n- Se há risco de novo acidente\n\nNão mova feridos graves sem orientação\nAcenda o pisca-alerta e coloque o triângulo a pelo menos 30 metros do veículo.")
            startActivity(intent)
        }

        val pre = findViewById<Button>(R.id.PRE)
        pre.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PRE))
            intent.putExtra("infos1", "- Acidentes em rodovias estaduais (CE-060, CE-187, etc)\n- Infrações graves em rodovias estaduais\n- Veículos suspeitos ou irregulares\n- Animais na pista")
            intent.putExtra("infos2", "- Nome da rodovia e KM aproximado\n- Número de veículos e feridos\n- Se há risco de novo acidente\n\nNão mova feridos graves sem orientação\nAcenda o pisca-alerta e coloque o triângulo a pelo menos 30 metros do veículo")
            startActivity(intent)
        }

        val samu = findViewById<Button>(R.id.SAMU)
        samu.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.SAMU))
            intent.putExtra("infos1", "- Infarto ou AVC\n- Acidentes com feridos graves\n- Overdose ou intoxicação\n- Dificuldade respiratória grave\n- Trabalho de parto em risco")
            intent.putExtra("infos2", "- Localização exata\n- Estado da vítima (consciente, respirando)\n- Idade aproximada\n- O que aconteceu\n\nNão mova a vítima sem orientação\nSiga todas as instruções do atendente até a equipe chegar")
            startActivity(intent)
        }

        val cb = findViewById<Button>(R.id.CB)
        cb.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CB))
            intent.putExtra("infos1", "- Incêndios fora de controle\n- Desabamentos\n- Afogamentos e resgates\n- Animais peçonhentos\n- Vazamento de gás")
            intent.putExtra("infos2", "- Localização exata\n- Tipo e tamanho do incêndio\n- Se há pessoas presas\n- Se há risco de explosão\n\nEvacue o local antes de ligar se possível\nNão tente apagar incêndios grandes sozinho\nFeche portas para retardar o avanço das chamas se puder")
            startActivity(intent)
        }

        val dc = findViewById<Button>(R.id.DC)
        dc.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.DC))
            intent.putExtra("infos1", "- Enchentes e alagamentos\n- Deslizamentos de terra\n- Vendavais e tempestades intensas\n- Estruturas em estado instável")
            intent.putExtra("infos2", "- Localização exata\n- Tipo de ocorrência\n- Se há pessoas em risco imediato\n\nAfaste-se de áreas de risco imediatamente\nNão retorne ao local sem autorização ou liberação por autoridades\nFique atento a alertas do sistema de sirenes ou por sms do município\nEm ultimo caso, refugie-se em abrigos próximos")
            startActivity(intent)
        }

        val cam = findViewById<Button>(R.id.CAM)
        cam.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CAM))
            intent.putExtra("infos1", "- Violência física ou psicológica\n- Ameaças e perseguição\n- Feminicídio em andamento\n- Denúncia anônima de agressão")
            intent.putExtra("infos2", "- Sua localização se estiver em segurança\n- O tipo de violência sofrida\n- Se o agressor está no local\n\nA ligação pode ser feita anonimamente.\nEm perigo imediato ligue também para a PM (190).")
            startActivity(intent)
        }

        val ddh = findViewById<Button>(R.id.DDH)
        ddh.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.DDH))
            intent.putExtra("infos1", "- Abuso de autoridade\n- Discriminação racial, social ou de gênero\n- Exploração de trabalho infantil\n- Trabalho escravo\n- Maus tratos a vulneráveis")
            intent.putExtra("infos2", "- O tipo de violação\n- Local onde ocorreu\n- Dados do responsável se souber\n\nA denúncia pode ser feita anonimamente\nGuarde evidências como fotos, vídeos e documentos se puder")
            startActivity(intent)
        }

        val procon = findViewById<Button>(R.id.PROCON)
        procon.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.PROCON))
            intent.putExtra("infos1", "- Fraudes em compras\n- Cobranças indevidas\n- Propaganda enganosa\n- Recusa de atendimento")
            intent.putExtra("infos2", "- Nome da empresa\n- O que aconteceu e quando\n- Valor envolvido se houver\n\nGuarde todos os comprovantes, notas fiscais e conversas com a empresa\nO PROCON pode mediar acordos e aplicar multas administrativas\nExija o código de defesa do consumidor (CDC) ")
            startActivity(intent)
        }

        val cvv = findViewById<Button>(R.id.CVV)
        cvv.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("service", getString(R.string.CVV))
            intent.putExtra("infos1", "- Pensamentos suicidas\n- Sofrimento emocional intenso\n- Crise de ansiedade ou desespero\n- Apoio emocional em geral")
            intent.putExtra("infos2", "O serviço é gratuito, anônimo, sigiloso e disponível 24 horas.\n\nVocê não precisa estar em crise para ligar.\nFalar sobre o que sente já é um passo importante\nSe preferir, o atendimento também está disponível pelo chat em cvv.org.br")
            startActivity(intent)
        }

    }
}