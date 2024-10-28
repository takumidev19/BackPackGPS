package com.example.backpackgps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ForgetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cambiarPantalla: MaterialButton = findViewById(R.id.btnRecuperar)
        val etNombreUsuario: TextInputEditText = findViewById(R.id.etNombreUsuario)

        cambiarPantalla.setOnClickListener {
            try {
                val nombreUsuario = etNombreUsuario.text.toString().trim()

                if (nombreUsuario.isNotEmpty()) {
                    val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val storedPassword = sharedPreferences.getString(nombreUsuario, null)

                    if (storedPassword != null) {
                        val intent = Intent(this, ResetPassword::class.java).apply {
                            putExtra("nombre_usuario", nombreUsuario)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "El nombre de usuario no existe", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    etNombreUsuario.error = "Por favor, introduce tu nombre de usuario."
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Ocurrió un error al intentar recuperar la cuenta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
