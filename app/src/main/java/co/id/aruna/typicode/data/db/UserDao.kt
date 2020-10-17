package co.id.aruna.typicode.data.db

import androidx.room.*
import co.id.aruna.typicode.data.model.UsersItem

@Dao
interface UserDao {
    @Query("SELECT * FROM usersitem")
    fun getAll(): List<UsersItem>

    @Insert
    fun insertAll(users: List<UsersItem>)

    @Query("DELETE FROM usersitem")
    fun delete()

    @Query("SELECT * FROM usersitem WHERE title LIKE '%' || :title || '%'")
    fun searchTitle(title: String): List<UsersItem>
}