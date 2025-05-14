package com.example.bogotrash.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.Recycler

class RecyclerAdapter(private var recyclers: List<Recycler>) :
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

        holder.contactButton.text = "Contactar"
        holder.contactButton.isEnabled = true
        holder.contactButton.setOnClickListener {
            // Funcionalidad por implementar
        }
    }

    override fun getItemCount(): Int = recyclers.size

    fun updateList(newList: List<Recycler>) {
        recyclers = newList
        notifyDataSetChanged()
    }
}
