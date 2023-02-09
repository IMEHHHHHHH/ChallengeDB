package com.example.challengeroom1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1.Room.Constant
import com.example.challengeroom1.Room.dbsmksa
import com.example.challengeroom1.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val db by lazy { dbsmksa(this) }
    private lateinit var siswaAdapter: SiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()
        setupRecyclerview()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbSiswaDAO().gettbSiswa()
            Log.d("Main Activity", "dbResponse: $siswa")
            withContext(Dispatchers.Main){
                siswaAdapter.setData(siswa)
            }
        }
    }
    private fun setUpListener(){
        catatan.setOnClickListener {
           intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(tbSiswanis: Int, intentType:Int){
        startActivity(Intent(applicationContext,EditActivity::class.java)
            .putExtra("intent_nis", tbSiswanis)
            .putExtra("intent_type", intentType)
        )
    }
    fun setupRecyclerview(){
        siswaAdapter = SiswaAdapter(arrayListOf(), object : SiswaAdapter.onAdapterListener{
            override fun onClick(tbSis: tbSiswa) {
                intentEdit(tbSis.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(tbSis: tbSiswa) {
                intentEdit(tbSis.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbSis: tbSiswa) {
                deleteAlert(tbSis)
            }
        })
        list_datasiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteAlert(tbsis: tbSiswa){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin Hapus ${tbsis.nama}?")
            setNegativeButton("Batal"){ dialogInterface,i->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus"){dialogInterface,i->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbSiswaDAO().deletetbSiswa(tbsis)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }
}