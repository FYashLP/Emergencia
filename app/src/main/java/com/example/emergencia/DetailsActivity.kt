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

        val local = findViewById<Button>(R.id.Local)
        local.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 2)
            }
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                localFinder()
            }
        }

        val service = intent.getStringExtra("service")
        val inf1 = intent.getStringExtra("infos1")
        val inf2 = intent.getStringExtra("infos2")
        val cellnum = service?.substring(0,3)

        findViewById<android.widget.TextView>(R.id.Service).text = service
        findViewById<android.widget.TextView>(R.id.infos1).text = inf1
        findViewById<android.widget.TextView>(R.id.infos2).text = inf2

        val call = findViewById<Button>(R.id.Call)
        call.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = "tel:$cellnum".toUri()
            startActivity(dialIntent)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.firstOrNull() == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                localFinder()
            }
        }
    }

    private fun localFinder() {
        val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager

        try {
            val location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)

            if (location != null) {

                Thread {
                    try {
                        val geocoder = android.location.Geocoder(this, java.util.Locale.getDefault())
                        val enderecos = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val endereco = enderecos?.firstOrNull()

                        val estado = endereco?.adminArea ?: ""
                        val cidade = endereco?.subAdminArea ?: ""
                        val bairro = endereco?.subLocality ?: ""
                        val rua = endereco?.thoroughfare ?: ""
                        val localiz = "$estado, $cidade, $bairro, $rua"

                        runOnUiThread {

                            android.widget.Toast.makeText(this, localiz, android.widget.Toast.LENGTH_LONG).show()

                            val channelId = "localNotif"
                            val channel = android.app.NotificationChannel(channelId, "LocalNotif", android.app.NotificationManager.IMPORTANCE_HIGH)
                            getSystemService(android.app.NotificationManager::class.java).createNotificationChannel(channel)

                            val notif = androidx.core.app.NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                                .setContentTitle("Sua localização: ")
                                .setContentText(localiz)
                                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                                .build()

                            androidx.core.app.NotificationManagerCompat.from(this).notify(1, notif)

                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            android.widget.Toast.makeText(this, "Erro ao obter endereço", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            } else {
                android.widget.Toast.makeText(this, "Não foi possível obter localização", android.widget.Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            android.widget.Toast.makeText(this, "Permissão negada", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}