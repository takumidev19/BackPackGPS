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

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreUsuario = intent.getStringExtra("nombre_usuario") ?: ""

        val etNuevaContrasena: TextInputEditText = findViewById(R.id.etContrasenaNueva)
        val etConfirmarContrasena: TextInputEditText = findViewById(R.id.etConfirmarContrasena)
        val cambiarPantalla: MaterialButton = findViewById(R.id.btnReiniciar)

        cambiarPantalla.setOnClickListener {
            try {
                val nuevaContrasena = etNuevaContrasena.text.toString()
                val confirmarContrasena = etConfirmarContrasena.text.toString()

                if (nuevaContrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()) {
                    if (nuevaContrasena == confirmarContrasena) {
                        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString(nombreUsuario, nuevaContrasena)
                        editor.apply()

                        Toast.makeText(this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        etConfirmarContrasena.error = "Las contraseñas no coinciden."
                    }
                } else {
                    if (nuevaContrasena.isEmpty()) {
                        etNuevaContrasena.error = "Por favor, introduce una nueva contraseña."
                    }
                    if (confirmarContrasena.isEmpty()) {
                        etConfirmarContrasena.error = "Por favor, confirma la nueva contraseña."
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Ocurrió un error al intentar cambiar la contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
