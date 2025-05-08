package com.example.bogotrash.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.bogotrash.databinding.FragmentEducationBinding
import com.example.bogotrash.view.WebViewActivity

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

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)

        binding.educationCard1.setOnClickListener {
            openWebView("https://bogota.gov.co/mi-ciudad/ambiente/como-hacer-separacion-de-residuos-y-reciclar-desde-casa")
        }

        binding.educationCard2.setOnClickListener {
            openWebView("https://bogota.gov.co/que-hacer/ambiente/plasticos-de-un-solo-uso-que-son-y-como-se-reduce-el-consumo")
        }

        binding.educationCard3.setOnClickListener {
            openWebView("https://bogota.gov.co/mi-ciudad/ambiente/como-reciclar-en-bogota")
        }
    }

    private fun openWebView(url: String) {
        val intent = Intent(requireContext(), WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

