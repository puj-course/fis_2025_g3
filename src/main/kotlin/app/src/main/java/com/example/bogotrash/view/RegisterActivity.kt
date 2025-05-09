package com.example.bogotrash.view

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.model.Recycler
import com.example.bogotrash.model.User
import com.example.bogotrash.repository.UserRepository
import androidx.core.view.WindowCompat

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_register)


        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val isRecyclerCheckBox = findViewById<CheckBox>(R.id.recyclerCheckBox)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val addressEditText = findViewById<EditText>(R.id.addressEditText)
        val zoneEditText = findViewById<EditText>(R.id.zoneEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        //Mostramos los campos del reciclador
        isRecyclerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if (isChecked) LinearLayout.VISIBLE else LinearLayout.GONE
            phoneEditText.visibility = visibility
            addressEditText.visibility = visibility
            zoneEditText.visibility = visibility
        }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(0, name, email, password)

            Thread {
                if (isRecyclerCheckBox.isChecked) {
                    val phone = phoneEditText.text.toString().trim()
                    val address = addressEditText.text.toString().trim()
                    val zone = zoneEditText.text.toString().trim()

                    if (phone.isEmpty() || address.isEmpty() || zone.isEmpty()) {
                        runOnUiThread {
                            Toast.makeText(this, "Faltan datos del reciclador", Toast.LENGTH_SHORT).show()
                        }
                        return@Thread
                    }

                    val recycler = Recycler(0, name, phone, address, zone)
                    UserRepository.registerRecycler(user, recycler)
                } else {
                    UserRepository.registerUser(user)
                }

                runOnUiThread {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}
