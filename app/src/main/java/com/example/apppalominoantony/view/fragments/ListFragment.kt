package pe.edu.idat.apppatitasidatccm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppalominoantony.databinding.FragmentListaBinding
import pe.edu.idat.appidatccmfirebase.adapter.PersonaAdapter
import pe.edu.idat.apppatitasidatccm.viewmodel.MascotaViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private lateinit var mascotaViewModel: MascotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaBinding.inflate(
            inflater, container, false)
        binding.rvmascota.layoutManager = LinearLayoutManager(
            requireActivity())
        mascotaViewModel = ViewModelProvider(requireActivity())
            .get(MascotaViewModel::class.java)
        listarUsuarios()
        return binding.root
    }

    private fun listarUsuarios() {
        mascotaViewModel.listarMascotas().observe(viewLifecycleOwner,
        Observer {
            binding.rvmascota.adapter = PersonaAdapter(it)
        })
    }

}