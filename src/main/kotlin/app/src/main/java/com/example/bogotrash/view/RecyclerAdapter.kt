package com.example.bogotrash.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.Recycler

class RecyclerAdapter(private val recyclers: List<Recycler>) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val zoneTextView: TextView = itemView.findViewById(R.id.zoneTextView)
        val contactButton: Button = itemView.findViewById(R.id.contactButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val recycler = recyclers[position]
        holder.nameTextView.text = recycler.name
        holder.phoneTextView.text = "Tel: ${recycler.phone}"
        holder.addressTextView.text = "Dir: ${recycler.address}"
        holder.zoneTextView.text = "Zona: ${recycler.zone}"

        // Simular estado de disponibilidad (puedes cambiar la lógica según la BD)
        val isAvailable = position != 3 // Simulamos que el último (Pedro) no está disponible
        holder.contactButton.text = "Contactar"
        holder.contactButton.isEnabled = isAvailable
        holder.contactButton.backgroundTintList = ContextCompat.getColorStateList(
            holder.itemView.context,
            if (isAvailable) R.color.Yellow else R.color.gray
        )
        holder.contactButton.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (isAvailable) R.color.Red else R.color.black
            )
        )

        // Acción para el botón "Contactar" (iniciar una llamada)
        holder.contactButton.setOnClickListener {
            if (isAvailable) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${recycler.phone}")
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = recyclers.size
}