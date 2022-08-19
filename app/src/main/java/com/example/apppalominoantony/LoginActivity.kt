package com.example.apppalominoantony

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppalominoantony.databinding.ActivityLoginBinding
import com.qbo.apppatitas2qbo.utilitarios.AppMensaje
import com.qbo.apppatitas2qbo.utilitarios.Constantes
import com.qbo.apppatitas2qbo.utilitarios.TipoMensaje
import pe.edu.idat.apppatitasidatccm.db.entity.PersonaEntity
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseLogin
import pe.edu.idat.apppatitasidatccm.utilitarios.SharedPreferencesManager
import pe.edu.idat.apppatitasidatccm.viewmodel.AuthViewModel
import pe.edu.idat.apppatitasidatccm.viewmodel.PersonaViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var personaViewModel: PersonaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        authViewModel = ViewModelProvider(this)
            .get(AuthViewModel::class.java)

        if(verificarCheckRecordarDatos()){
            binding.chkrecordar.isChecked = true
            binding.etusuario.isEnabled = false
            binding.etpassword.isEnabled = false
            binding.chkrecordar.text = "Quitar el check para ingresar con otro usuario"
            //Obtener los datos de SQLite
            personaViewModel.obtener().observe(
                this, Observer { persona ->
                       persona?.let {
                           binding.etusuario.setText(persona.usuario)
                           binding.etpassword.setText(persona.password)
                       }
                })
        }else{
            personaViewModel.eliminarTodo()
        }
        binding.chkrecordar.setOnClickListener(this)
        binding.btnlogin.setOnClickListener(this)
        binding.btnregistrar.setOnClickListener(this)
        authViewModel.responseLogin.observe(this, Observer {
            response -> obtenerDatosLogin(response)
        })
    }
    fun verificarCheckRecordarDatos(): Boolean{
        return SharedPreferencesManager().getSomeBooleanValue(
            Constantes().PREF_RECORDAR)
    }

    private fun obtenerDatosLogin(response: ResponseLogin) {
        if(response.rpta){
            val personaEntity = PersonaEntity(
                response.idpersona, response.nombres,
                response.apellidos, response.email, response.celular,
                response.usuario, response.password, response.esvoluntario
            )
            if(verificarCheckRecordarDatos()){
                personaViewModel.actualizar(personaEntity)
            }else{
                personaViewModel.insertar(personaEntity)
                if(binding.chkrecordar.isChecked){
                    SharedPreferencesManager().setSomeBooleanValue(
                        Constantes().PREF_RECORDAR, true)
                }
            }
            startActivity(Intent(applicationContext,
                HomeActivity::class.java))
            finish()
        }else{
            AppMensaje.enviarMensaje(binding.root, response.mensaje,
                TipoMensaje.ERROR)
        }
        binding.btnregistrar.isEnabled = true
        binding.btnlogin.isEnabled = true
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnlogin -> autenticarUsuario()
            R.id.btnregistrar -> startActivity(Intent(applicationContext,
                RegistroActivity::class.java))
            R.id.chkrecordar -> setearValoresRecordar(vista)
        }
    }

    private fun setearValoresRecordar(vista: View) {
        if(vista is CheckBox){
            val checked = vista.isChecked
            if(!checked){
                if(verificarCheckRecordarDatos()){
                    SharedPreferencesManager().deletePreference(
                        Constantes().PREF_RECORDAR)
                    personaViewModel.eliminarTodo()
                    binding.etusuario.isEnabled = true
                    binding.etpassword.isEnabled = true
                    binding.chkrecordar.text = getString(R.string.recordarlogin)
                }
            }
        }
    }

    private fun autenticarUsuario() {
        binding.btnregistrar.isEnabled = false
        binding.btnlogin.isEnabled = false
        if(validarUsuarioPassword()){
            authViewModel.autenticarUsuario(binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
        }else{
            AppMensaje.enviarMensaje(binding.root,
                getString(R.string.msgloginincompleto),
                TipoMensaje.ERROR)
            binding.btnregistrar.isEnabled = true
            binding.btnlogin.isEnabled = true
        }
    }
    private fun validarUsuarioPassword(): Boolean {
        var okValidacion= true
        if(binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            okValidacion= false
        }else if(binding.etpassword.text.toString().trim().isEmpty()){
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            okValidacion= false
        }
        return okValidacion
    }
}