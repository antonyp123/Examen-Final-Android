package pe.edu.idat.apppatitasidatccm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.apppatitasidatccm.repository.MascotaRepository
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseMascota
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseRegistro

class MascotaViewModel: ViewModel() {
    private var repository = MascotaRepository()
    var responseRegistro: LiveData<ResponseRegistro> = repository.responseRegistro

    fun listarMascotas(): LiveData<List<ResponseMascota>>{
        return repository.listarMascotas()
    }

    fun registrarVoluntario(idPersona: Int){
        responseRegistro = repository.registrarVoluntario(idPersona)
    }

}