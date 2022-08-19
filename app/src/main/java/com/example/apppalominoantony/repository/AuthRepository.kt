package pe.edu.idat.apppatitasidatccm.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.apppatitasidatccm.retrofit.UsuariosCliente
import pe.edu.idat.apppatitasidatccm.retrofit.request.RequestLogin
import pe.edu.idat.apppatitasidatccm.retrofit.request.RequestRegistro
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseRegistro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    var loginReponse = MutableLiveData<ResponseLogin>()
    var registroReponse = MutableLiveData<ResponseRegistro>()

    fun autenticarUsuario(requestLogin: RequestLogin)
    : MutableLiveData<ResponseLogin>
    {
        val call: Call<ResponseLogin> = UsuariosCliente
            .retrofitService.login(requestLogin)
        call.enqueue(object: Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                loginReponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("ErrorLogin", t.message.toString())
            }
        })
        return loginReponse
    }
    fun registroUsuario(requestRegistro: RequestRegistro)
            : MutableLiveData<ResponseRegistro>
    {
        val call: Call<ResponseRegistro> = UsuariosCliente
            .retrofitService.registro(requestRegistro)
        call.enqueue(object: Callback<ResponseRegistro>{
            override fun onResponse(
                call: Call<ResponseRegistro>,
                response: Response<ResponseRegistro>
            ) {
                registroReponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {
                Log.e("ErrorLogin", t.message.toString())
            }

        })
        return registroReponse
    }

}