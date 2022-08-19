package pe.edu.idat.apppatitasidatccm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pe.edu.idat.apppatitasidatccm.db.entity.PersonaEntity

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertar(vararg persona: PersonaEntity)

    @Update
    fun actualizar(vararg persona: PersonaEntity)

    @Query("DELETE FROM persona")
    fun eliminarTodo()

    @Query("SELECT * FROM persona LIMIT 1")
    fun obtener(): LiveData<PersonaEntity>

}