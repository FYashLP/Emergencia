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

        // Botão de voltar, agindo fechando a view de detalhes
        val voltar = findViewById<ImageButton>(R.id.Back)
        voltar.setOnClickListener { finish() }

        // Bloco da função de discagem rapida abaixo;
        val service = intent.getStringExtra("service")  // val para receber o que foi mandado (string) da main view pelo putExtra no intent
        val inf1 = intent.getStringExtra("infos1") // val para receber o que foi mandado (string) da main view pelo putExtra no intent
        val inf2 = intent.getStringExtra("infos2") // val para receber o que foi mandado (string) da main view pelo putExtra no intent
        val cellnum = service?.substring(0,3) // Aqui uma val recebe os 3 primeiros chars da string service (a de contato e nome do serviço, servindo mais para retirar o num. de contato ja que todas começam por 3 numerais)

        // Aqui é onde os textviews mencionados (Service, Infos1 e Infos 2) recebem as strings das vals anteriores, onde cada uma irá aparecer o serviço e as informações do botão selecionado
        findViewById<android.widget.TextView>(R.id.Service).text = service // os textos do textview recebendo sua val correspondente
        findViewById<android.widget.TextView>(R.id.infos1).text = inf1 // os textos do textview recebendo sua val correspondente
        findViewById<android.widget.TextView>(R.id.infos2).text = inf2 // os textos do textview recebendo sua val correspondente

        // Aqui é a função de discagem rapida em sí
        val call = findViewById<Button>(R.id.Call) // Linkando o botão do evento
        call.setOnClickListener { // No clique;
            val dialIntent = Intent(Intent.ACTION_DIAL) // Redireciona o usuario a tela de discagem nativa do seu telefone
            dialIntent.data = "tel:$cellnum".toUri() // Em que os dados de passagem são a subtring acima que pegou os 3 primeiros char, parseados para o discador
            startActivity(dialIntent) // starta o dialIntent
        }

        // Bloco voltado para as permissões do aplicativo (primeira vez)
        val local = findViewById<Button>(R.id.Local)
        local.setOnClickListener { // Ao clicar no botão "onde estou" ;
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1) // Requere a permissão de localização caso a permissão não foi concedida ainda
            }
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 2) // // E requere a permissão de notificação
            }
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                localFinder() // Se as duas permissões foram concedidas, roda a função de localização (mais detalhes abaixo)
            }
        }
    }

    // Bloco voltado para as permissões do aplicativo (vezes depois da primeira, caso o usuario desabilite mais a frente, serve como recheck)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.firstOrNull() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                localFinder() // Roda o localizador se ambas permissões fossem garantidas
            }
        }
    }

    // Bloco da função de localização em sí
    private fun localFinder() {
        val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager //inicializa a função nativa de GPS do android

        try {
            val location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER) // try para determinação do local, no caso do aparelho estiver offline, se não retornar (?:) vai pro fallback da rede
                ?: locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER) // fallback da rede se conectado, o que foi recebido não foi tratados, a val serve mais como prova de existencia

            if (location != null) { // Se nenhuma das determinações retornou null

                Thread { //run no background
                    try {
                        val geocoder = android.location.Geocoder(this, java.util.Locale.getDefault()) // val geocoder recebe um set de coordenadas (da val location) do host
                        val enderecos = geocoder.getFromLocation(location.latitude, location.longitude, 1) // val para receber as coordenadas extraidas (getFromLocation) da val geocoder
                        val endereco = enderecos?.firstOrNull() // para um catch adiante caso o set venha null

                        val estado = endereco?.adminArea ?: "" // Retorna o parametro pra val ou nada
                        val cidade = endereco?.subAdminArea ?: "" // Retorna o parametro pra val ou nada
                        val bairro = endereco?.subLocality ?: "" // Retorna o parametro pra val ou nada -//- aqui é meio tricky, pelo que li, locality era pra ser onde recebia o set da cidade, MAS aqui no brasil é subLocality em vez de locality, que retorna nada
                        val rua = endereco?.thoroughfare ?: "" // Retorna o parametro pra val ou nada
                        val localiz = "$estado, $cidade, $bairro, $rua" // Concatena o valor das val acima numa string só

                        runOnUiThread {

                            android.widget.Toast.makeText(this, localiz, android.widget.Toast.LENGTH_LONG).show() // O primeiro sinal externo da função rodando, um toast (msg em cinza breze ao fundo) da string (localização atual do usuário)

                            val channelId = "localNotif" // val para servir como canal da notificação (Aqui mais no intuito de a pessoa estiver em meio a ligação, o toast short ou long pode sumir, então fica ja na notificação que e só baixar a aba para ler)
                            val channel = android.app.NotificationChannel(channelId, "LocalNotif", android.app.NotificationManager.IMPORTANCE_HIGH) // o canal em sí, acima de todos
                            getSystemService(android.app.NotificationManager::class.java).createNotificationChannel(channel)

                            val notif = androidx.core.app.NotificationCompat.Builder(this, channelId) //bloco usado como builder da notificaçõa, val é a notificação em si puxando os parametros abaixo
                                .setSmallIcon(android.R.drawable.ic_menu_mylocation) // icone
                                .setContentTitle("Sua localização: ") // pretexto
                                .setContentText(localiz) // string da localização concatenada previamente, a mesma informação do toast
                                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH) // prioridade
                                .build() // BUILDer

                            androidx.core.app.NotificationManagerCompat.from(this).notify(1, notif) // Lança a val (notif) como notificação

                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            android.widget.Toast.makeText(this, "Erro ao obter endereço", android.widget.Toast.LENGTH_SHORT).show() // Toast caso retorne null (o tal do catch adiante)
                        }
                    }
                }.start() // Como dito mais acima no If, se não retornou null, executa o bloco, mandando o toast e a notificação
            } else {
                android.widget.Toast.makeText(this, "Não foi possível obter localização", android.widget.Toast.LENGTH_SHORT).show() // Toast se location retornou null
            }
        } catch (e: SecurityException) {
            android.widget.Toast.makeText(this, "Permissão negada", android.widget.Toast.LENGTH_SHORT).show() // A função não roda se a perm. não foi concedida
        }
    }
}