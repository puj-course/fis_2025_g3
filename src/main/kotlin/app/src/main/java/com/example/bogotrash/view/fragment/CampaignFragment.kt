package com.example.bogotrash.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bogotrash.databinding.FragmentCampaignBinding

class CampaignFragment : Fragment() {

    private var _binding: FragmentCampaignBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón Participar
        binding.participateButton.setOnClickListener {
            // Acción al hacer clic (puedes abrir un detalle de la campaña)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}