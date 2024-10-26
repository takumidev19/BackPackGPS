package com.example.backpackgps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.backpackgps.databinding.CardItemBinding
import com.example.backpackgps.models.Dispositivo

class DispositivoAdapter(
    private var dispositivos: MutableList<Dispositivo>, // Haz que esta lista sea mutable
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DispositivoAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onEditarClick(dispositivo: Dispositivo)
        fun onEliminarClick(dispositivo: Dispositivo)
    }

    inner class ViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dispositivo: Dispositivo) {
            binding.textViewNombreDispositivo.text = dispositivo.nombre
            binding.textViewDetallesDispositivo.text = dispositivo.detalles

            binding.btnEditar.setOnClickListener {
                listener.onEditarClick(dispositivo)
            }

            binding.btnEliminar.setOnClickListener {
                listener.onEliminarClick(dispositivo)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dispositivos[position])
    }

    override fun getItemCount() = dispositivos.size

    // Método para actualizar la lista de dispositivos
    fun updateDispositivos(newDispositivos: List<Dispositivo>) {
        val oldSize = dispositivos.size
        dispositivos.clear() // Limpia la lista actual
        dispositivos.addAll(newDispositivos) // Agrega todos los nuevos dispositivos

        // Notifica los cambios específicos
        notifyItemRangeRemoved(0, oldSize) // Notifica que se han eliminado ítems
        notifyItemRangeInserted(0, newDispositivos.size) // Notifica que se han insertado nuevos ítems
    }
}


