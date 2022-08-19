package pe.edu.idat.apppatitasidatccm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apppalominoantony.databinding.ActivityRegistroBinding
import com.qbo.apppatitas2qbo.utilitarios.AppMensaje
import com.qbo.apppatitas2qbo.utilitarios.TipoMensaje
import pe.edu.idat.apppatitasidatccm.R
import pe.edu.idat.apppatitasidatccm.databinding.ActivityRegistroBinding
import pe.edu.idat.apppatitasidatccm.retrofit.response.ResponseRegistro
import pe.edu.idat.apppatitasidatccm.viewmodel.AuthViewModel

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this)
            .get(AuthViewModel::class.java)
        binding.btnirlogin.setOnClickListener(this)
        binding.btnregistrarme.setOnClickListener(this)
        authViewModel.responseRegistro.observe(this, Observer {
            response -> obtenerResultadoRegistro(response)
        })
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnregistrarme -> registrarPersona()
            R.id.btnirlogin -> startActivity(Intent(
                applicationContext, LoginActivity::class.java
            ))
        }
    }

    private fun registrarPersona() {
        binding.btnregistrarme.isEnabled = false
        binding.btnirlogin.isEnabled = false
        if(validarFormulario()){
            authViewModel.registrarUsuario(
                binding.etnomusuario.text.toString(),
                binding.etapeusuario.text.toString(),
                binding.etemailusuario.text.toString(),
                binding.etcelusuario.text.toString(),
                binding.etusureg.text.toString(),
                binding.etpassreg.text.toString(),
            )
        }else{
            binding.btnregistrarme.isEnabled = true
            binding.btnirlogin.isEnabled = true
        }
    }

    private fun setearControles(){
        binding.etnomusuario.setText("")
        binding.etapeusuario.setText("")
        binding.etemailusuario.setText("")
        binding.etcelusuario.setText("")
        binding.etusureg.setText("")
        binding.etpassreg.setText("")
        binding.etrepassreg.setText("")
    }
    private fun obtenerResultadoRegistro(response: ResponseRegistro){
        if(response.rpta){
            setearControles()
            AppMensaje.enviarMensaje(binding.root,
                response.mensaje, TipoMensaje.EXITO)
        }else{
            AppMensaje.enviarMensaje(binding.root,
                response.mensaje, TipoMensaje.ERROR)
        }
        binding.btnirlogin.isEnabled = true
        binding.btnregistrarme.isEnabled = true
    }
    private fun validarFormulario(): Boolean {
        var respuesta = true
        var mensaje = ""
        when{
            binding.etnomusuario.text.toString().trim().isEmpty() -> {
                binding.etnomusuario.isFocusableInTouchMode = true
                binding.etnomusuario.requestFocus()
                mensaje = "Ingrese su nombre"
                respuesta = false
            }
            binding.etapeusuario.text.toString().trim().isEmpty() -> {
                binding.etapeusuario.isFocusableInTouchMode = true
                binding.etapeusuario.requestFocus()
                mensaje = "Ingrese su apellido"
                respuesta = false
            }
            binding.etemailusuario.text.toString().trim().isEmpty() -> {
                binding.etemailusuario.isFocusableInTouchMode = true
                binding.etemailusuario.requestFocus()
                mensaje = "Ingrese su email"
                respuesta = false
            }
            binding.etcelusuario.text.toString().trim().isEmpty() -> {
                binding.etcelusuario.isFocusableInTouchMode = true
                binding.etcelusuario.requestFocus()
                mensaje = "Ingrese su celular"
                respuesta = false
            }
            binding.etpassreg.text.toString().trim().isEmpty() -> {
                binding.etpassreg.isFocusableInTouchMode = true
                binding.etpassreg.requestFocus()
                mensaje = "Ingrese su password"
                respuesta = false
            }
            binding.etrepassreg.text.toString().trim().isEmpty() -> {
                binding.etrepassreg.isFocusableInTouchMode = true
                binding.etrepassreg.requestFocus()
                mensaje = "Ingrese la confirmación de su password"
                respuesta = false
            }
            binding.etrepassreg.text.toString().trim().isNotEmpty() -> {
                if(binding.etpassreg.text.toString() !=
                    binding.etrepassreg.text.toString()){
                    binding.etrepassreg.isFocusableInTouchMode = true
                    binding.etrepassreg.requestFocus()
                    mensaje = "Password no coinciden"
                    respuesta = false
                }
            }
        }
        if(!respuesta) AppMensaje.enviarMensaje(binding.root,
            mensaje, TipoMensaje.ERROR)
        return respuesta
    }
}