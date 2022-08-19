package com.example.apppalominoantony.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.idat.apppatitasidatccm.db.dao.PersonaDao
import pe.edu.idat.apppatitasidatccm.db.entity.PersonaEntity

@Database(entities = [PersonaEntity::class], version = 1)
public abstract class UsuariosDatabase: RoomDatabase() {

    abstract fun personaDao(): PersonaDao
    //Todo lo que tenga este bloque serán definidos como static
    companion object{
        @Volatile
        private var INSTANCE: UsuariosDatabase? = null

        fun getDatabase(context: Context) : UsuariosDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuariosDatabase::class.java,
                    "patitasdb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}