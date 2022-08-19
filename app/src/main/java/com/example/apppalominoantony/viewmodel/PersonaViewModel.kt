package pe.edu.idat.apppatitasidatccm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.apppalominoantony.entity.UsuariosDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.idat.apppatitasidatccm.db.entity.PersonaEntity
import pe.edu.idat.apppatitasidatccm.repository.PersonaRepository

class PersonaViewModel(application: Application)
    : AndroidViewModel(application) {

        private val repository : PersonaRepository

        init {
            val personaDao = UsuariosDatabase.getDatabase(application)
                .personaDao()
            repository = PersonaRepository(personaDao)
        }

        fun insertar(personaEntity: PersonaEntity) =
            viewModelScope.launch(Dispatchers.IO){
                repository.insertar(personaEntity)
            }
            fun actualizar(personaEntity: PersonaEntity)=
                viewModelScope.launch(Dispatchers.IO) {
                    repository.actualizar(personaEntity)
                }

            fun eliminarTodo() =
                viewModelScope.launch(Dispatchers.IO){
                    repository.eliminarTodo()
                }
            fun obtener(): LiveData<PersonaEntity>{
                return repository.obtener()
            }

}