package pe.edu.idat.apppatitasidatccm.utilitarios

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.qbo.apppatitas2qbo.utilitarios.MiApp

class SharedPreferencesManager {
    private val APP_SETTINGS_FILE = "APP_SETTINGS"

    private fun getSharedPreferences(): SharedPreferences {
        return MiApp.applicationContext.getSharedPreferences(
            APP_SETTINGS_FILE, MODE_PRIVATE)
    }

    fun setSomeBooleanValue(nombre: String, valor: Boolean){
        val editor = getSharedPreferences().edit()
        editor.putBoolean(nombre, valor)
        editor.commit()
    }
    fun getSomeBooleanValue(nombre: String) : Boolean{
        return getSharedPreferences().getBoolean(nombre, false)
    }
    fun deletePreference(nombre: String){
        getSharedPreferences().edit().remove(nombre).apply()
    }
    fun setSomeStringValue(nombre: String, valor: String){
        val editor = getSharedPreferences().edit()
        editor.putString(nombre, valor)
        editor.commit()
    }
    fun getSomeStringValue(nombre: String) : String{
        return getSharedPreferences().getString(nombre, "").toString()
    }


}