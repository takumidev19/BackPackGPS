package com.example.backpackgps

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Register : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCrearCuenta: MaterialButton = findViewById(R.id.btnCrearCuenta)
        btnCrearCuenta.setOnClickListener {
            registerUser()
        }

        val cambiarPantalla2: TextView = findViewById(R.id.TvTienesCuenta)
        cambiarPantalla2.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        try {
            val username = findViewById<TextInputEditText>(R.id.etNombreUsuario).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.etPassword).text.toString()
            val passwordConfirm = findViewById<TextInputEditText>(R.id.etPasswordConfirm).text.toString()

            if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return
            }

            if (password != passwordConfirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return
            }

            val storedPassword = sharedPreferences.getString(username, null)
            if (storedPassword != null) {
                Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show()
                return
            }

            val editor = sharedPreferences.edit()
            editor.putString(username, password)
            editor.apply()

            Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
