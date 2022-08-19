package pe.edu.idat.apppatitasidatccm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.apppatitasidatccm.repository.AuthRepository
import pe.edu.idat.apppatitasidatccm.retrofit.request.RequestLogin
import pe.edu.idat.apppatitasidatccm.retrofit.request.RequestRegistro
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseRegistro

class AuthViewModel : ViewModel() {

    var responseLogin: LiveData<ResponseLogin>
    var responseRegistro: LiveData<ResponseRegistro>
    private var repository = AuthRepository()

    init {
        responseLogin = repository.loginReponse
        responseRegistro = repository.registroReponse
    }

    fun autenticarUsuario(usuario: String, password: String){
        responseLogin = repository.autenticarUsuario(
            RequestLogin(usuario, password)
        )
    }
    fun registrarUsuario(nombres: String, apellidos: String,
    email: String, celular: String, usuario: String, password: String){
        responseRegistro = repository.registroUsuario(
            RequestRegistro(nombres, apellidos, email,
                celular, usuario, password)
        )
    }


}