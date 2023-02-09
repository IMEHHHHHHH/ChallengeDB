package com.example.challengeroom1.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbSiswa(
    @PrimaryKey
    val nis: Int,
    val nama: String,
    val  kelas:String,
    val alamat:String)