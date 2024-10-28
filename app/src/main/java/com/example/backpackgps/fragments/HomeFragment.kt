package com.example.backpackgps.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.backpackgps.R
import com.example.backpackgps.adapters.DispositivoAdapter
import com.example.backpackgps.databinding.FragmentHomeBinding
import com.example.backpackgps.models.Dispositivo
import com.example.backpackgps.viewmodels.DispositivoViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dispositivoViewModel: DispositivoViewModel by activityViewModels()

    private lateinit var adapter: DispositivoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = DispositivoAdapter(
            dispositivoViewModel.dispositivos.value?.toMutableList() ?: mutableListOf(),
            object : DispositivoAdapter.OnItemClickListener {
                override fun onEditarClick(dispositivo: Dispositivo) {
                    try {
                        showEditDialog(dispositivo)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al editar el dispositivo", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onEliminarClick(dispositivo: Dispositivo) {
                    try {
                        dispositivoViewModel.removeDispositivo(dispositivo)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al eliminar el dispositivo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        try {
            binding.recyclerViewDispositivos.layoutManager = LinearLayoutManager(context)
            binding.recyclerViewDispositivos.adapter = adapter

            dispositivoViewModel.dispositivos.observe(viewLifecycleOwner) { dispositivos ->
                adapter.updateDispositivos(dispositivos)
            }

            binding.btnAgregarDispositivo.setOnClickListener {
                try {
                    val nuevoDispositivo = Dispositivo("Nuevo Dispositivo", "Detalles del dispositivo")
                    dispositivoViewModel.addDispositivo(nuevoDispositivo)
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al agregar el dispositivo", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al inicializar la lista de dispositivos", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun showEditDialog(dispositivo: Dispositivo) {
        try {
            val builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_edit_dispositivo, null)
            builder.setView(dialogView)

            val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
            val editTextDetalles = dialogView.findViewById<EditText>(R.id.editTextDetalles)

            editTextNombre.setText(dispositivo.nombre)
            editTextDetalles.setText(dispositivo.detalles)

            builder.setTitle("Editar Dispositivo")
                .setPositiveButton("Guardar") { _, _ ->
                    try {
                        val nuevoNombre = editTextNombre.text.toString()
                        val nuevosDetalles = editTextDetalles.text.toString()

                        val dispositivoActualizado = dispositivo.copy(nombre = nuevoNombre, detalles = nuevosDetalles)

                        dispositivoViewModel.removeDispositivo(dispositivo)
                        dispositivoViewModel.addDispositivo(dispositivoActualizado)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al guardar los cambios", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

            val dialog = builder.create()
            dialog.show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error al mostrar el diálogo de edición", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
