package pe.edu.idat.appidatccmfirebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apppalominoantony.databinding.PersonaItemBinding

import pe.edu.idat.appidatccmfirebase.model.Persona
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseMascota

class PersonaAdapter(private var lstpersona: List<ResponseMascota>)
    : RecyclerView.Adapter<PersonaAdapter.ViewHolder>()
{
        inner class ViewHolder(val binding: PersonaItemBinding) :
                RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonaItemBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(lstpersona[position]){
                binding.tvnompersona.text = nombre
                binding.tvapepersona.text = apellido
                binding.tvedadpersona.text = edad.toString()
            }
        }
    }
    override fun getItemCount(): Int {
        return lstpersona.size
    }
}