package com.example.challengeroom1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom1.Room.Constant
import com.example.challengeroom1.Room.dbsmksa
import com.example.challengeroom1.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val  db by lazy { dbsmksa(this) }
    private var tbSiswanis: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        tombolperintah()
        setupView()
        tampilsiswa()

        tbSiswanis = intent.getIntExtra("intent_nis", tbSiswanis)
        Toast.makeText(this,tbSiswanis.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE->{
            }
            Constant.TYPE_READ->{
                button_update.visibility = View.GONE
                button_save.visibility = View.GONE
                edit_title.visibility = View.GONE
                tampilsiswa()
            }
            Constant.TYPE_UPDATE ->{
                edit_title.visibility = View.GONE
                button_save.visibility = View.GONE
                tampilsiswa()
            }

        }
    }
    private fun tombolperintah(){
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbSiswaDAO().addtbSiswa(
                    tbSiswa(edit_title.text.toString().toInt(),edit_name.text.toString(),
                        edit_note.text.toString(),edit_maps.text.toString())
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbSiswaDAO().updatetbSiswa(
                    tbSiswa(tbSiswanis,edit_name.text.toString(),
                        edit_note.text.toString(),edit_maps.text.toString())
                )
                finish()
            }
        }
    }
    fun tampilsiswa(){
        tbSiswanis = intent.getIntExtra("intent_nis", 0)
        CoroutineScope(Dispatchers.IO).launch{
            val siswa = db.tbSiswaDAO().gettbSiswa(tbSiswanis).get(0)
            edit_name.setText(siswa.nama)
            edit_note.setText(siswa.kelas)
            edit_maps.setText(siswa.alamat)
        }
    }
}