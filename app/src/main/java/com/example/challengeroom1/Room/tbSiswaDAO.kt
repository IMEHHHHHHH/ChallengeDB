package com.example.challengeroom1.Room

import androidx.room.*

@Dao
interface tbSiswaDAO {

    @Insert
    fun addtbSiswa(tbSis: tbSiswa)

    @Update
    fun updatetbSiswa(tbSis: tbSiswa)

    @Delete
    fun deletetbSiswa(tbSis: tbSiswa)

    @Query("SELECT * FROM tbSiswa")
    fun gettbSiswa(): List<tbSiswa>

    @Query("SELECT * FROM tbSiswa WHERE nis=:tbSiswa_nis")
    fun gettbSiswa(tbSiswa_nis: Int): List<tbSiswa>
}