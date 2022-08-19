package pe.edu.idat.appidatccmfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppalominoantony.databinding.ActivityPersonasBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

import pe.edu.idat.appidatccmfirebase.model.Persona

class PersonasActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPersonasBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lstpersonas : ArrayList<Persona> = ArrayList()
        binding.rvpersonas.layoutManager = LinearLayoutManager(this)
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Persona")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.e("ErrorFirestore", error.message.toString())
                }
                for(documento in value!!.documentChanges){
                    if(documento.type == DocumentChange.Type.ADDED){
                        lstpersonas.add(Persona(
                            documento.document.data["nombre"].toString(),
                            documento.document.data["apellido"].toString(),
                            documento.document.data["edad"].toString().toInt()
                        ))
                    }
                }
                //binding.rvpersonas.adapter = PersonaAdapter(lstpersonas)
            }
    }
}