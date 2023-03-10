package com.example.challengeroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1.Room.tbSiswa
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.activity_siswa_adapter.view.*

class SiswaAdapter (private val siswa:ArrayList<tbSiswa>, private val listener: onAdapterListener)
    : RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>(){

    class SiswaViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        return SiswaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_siswa_adapter,
            parent, false)
        )
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        val sis = siswa[position]
        holder.view.TV_nama.text = sis.nama
        holder.view.TV_nama.setOnClickListener { listener.onClick(sis)}
        holder.view.image_edit.setOnClickListener { listener.onUpdate(sis) }
        holder.view.image_delete.setOnClickListener { listener.onDelete(sis) }
        }


    override fun getItemCount() = siswa.size

    fun setData(list: List<tbSiswa>){
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onClick(tbSis: tbSiswa)
        fun onUpdate(tbSis: tbSiswa)
        fun onDelete(tbSis: tbSiswa)
    }
}