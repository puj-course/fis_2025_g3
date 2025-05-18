package com.example.bogotrash.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bogotrash.R
import androidx.core.view.WindowCompat

class SendSmsActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    private var phoneNumber: String? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) sendSms()
            else Toast.makeText(this, "Permiso denegado para enviar SMS", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_send_sms)

        nameTextView = findViewById(R.id.recyclerNameTextView)
        phoneTextView = findViewById(R.id.recyclerPhoneTextView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendSmsButton)

        val recyclerName = intent.getStringExtra("recycler_name") ?: "Desconocido"
        phoneNumber = intent.getStringExtra("recycler_phone")

        nameTextView.text = "Reciclador: $recyclerName"
        phoneTextView.text = "Teléfono: $phoneNumber"

        sendButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSms()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private fun sendSms() {
        val message = messageEditText.text.toString()
        if (message.isBlank() || phoneNumber.isNullOrBlank()) {
            Toast.makeText(this, "Faltan datos para enviar el SMS", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al enviar SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
