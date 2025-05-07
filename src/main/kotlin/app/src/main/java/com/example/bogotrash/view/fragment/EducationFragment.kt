package com.example.bogotrash.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bogotrash.databinding.FragmentEducationBinding

class EducationFragment : Fragment() {

    private var _binding: FragmentEducationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEducationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar clics en las tarjetas (opcional, puedes agregar acciones)
        binding.educationCard1.setOnClickListener {
            // Acción al hacer clic (por ejemplo, mostrar más detalles)
        }
        binding.educationCard2.setOnClickListener {
            // Acción al hacer clic
        }
        binding.educationCard3.setOnClickListener {
            // Acción al hacer clic
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}