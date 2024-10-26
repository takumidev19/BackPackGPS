package com.example.backpackgps.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

        // Inicializa el adaptador con una lista mutable
        adapter = DispositivoAdapter(
            dispositivoViewModel.dispositivos.value?.toMutableList() ?: mutableListOf(),
            object : DispositivoAdapter.OnItemClickListener {
                override fun onEditarClick(dispositivo: Dispositivo) {
                    showEditDialog(dispositivo)
                }

                override fun onEliminarClick(dispositivo: Dispositivo) {
                    dispositivoViewModel.removeDispositivo(dispositivo)
                }
            }
        )

        binding.recyclerViewDispositivos.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDispositivos.adapter = adapter

        dispositivoViewModel.dispositivos.observe(viewLifecycleOwner) { dispositivos ->
            adapter.updateDispositivos(dispositivos) // Llama a un método para actualizar la lista
        }

        binding.btnAgregarDispositivo.setOnClickListener {
            val nuevoDispositivo = Dispositivo("Nuevo Dispositivo", "Detalles del dispositivo")
            dispositivoViewModel.addDispositivo(nuevoDispositivo)
        }

        return binding.root
    }

    private fun showEditDialog(dispositivo: Dispositivo) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_edit_dispositivo, null)
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val editTextDetalles = dialogView.findViewById<EditText>(R.id.editTextDetalles)

        // Pre-cargar los datos del dispositivo
        editTextNombre.setText(dispositivo.nombre)
        editTextDetalles.setText(dispositivo.detalles)

        builder.setTitle("Editar Dispositivo")
            .setPositiveButton("Guardar") { _, _ ->
                // Obtener los nuevos datos
                val nuevoNombre = editTextNombre.text.toString()
                val nuevosDetalles = editTextDetalles.text.toString()

                // Crear un nuevo objeto de dispositivo con los nuevos datos
                val dispositivoActualizado = dispositivo.copy(nombre = nuevoNombre, detalles = nuevosDetalles)

                // Actualizar el ViewModel
                dispositivoViewModel.removeDispositivo(dispositivo) // Primero eliminamos el antiguo
                dispositivoViewModel.addDispositivo(dispositivoActualizado) // Luego agregamos el actualizado
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
