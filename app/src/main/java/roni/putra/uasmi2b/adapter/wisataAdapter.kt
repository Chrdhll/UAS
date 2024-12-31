package roni.putra.uasmi2b.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import roni.putra.uasmi2b.DetailWisataActivity
import roni.putra.uasmi2b.R

import roni.putra.uasmi2b.model.wisataResponse

class wisataAdapter (
    val dataWisata: ArrayList<wisataResponse.ListItems>

) : RecyclerView.Adapter<wisataAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgWisata = view.findViewById<ImageView>(R.id.imgWisata)
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvNoTlp = view.findViewById<TextView>(R.id.tvNoTlp)
        val tvAlamat = view.findViewById<TextView>(R.id.tvAlamat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_wisata,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataWisata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Tampilkan data
        val hasilResponse = dataWisata[position]
        Picasso.get().load(hasilResponse.gambar).into(holder.imgWisata)
        holder.tvNama.text = hasilResponse.nama_wisata
        holder.tvNoTlp.text = hasilResponse.notlp
        holder.tvAlamat.text = hasilResponse.alamat


        //klik item berita
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailWisataActivity::class.java).apply {
                putExtra("gambar",hasilResponse.gambar)
                putExtra("nama",hasilResponse.nama_wisata)
                putExtra("notlp",hasilResponse.notlp)
                putExtra("alamat",hasilResponse.alamat)
                putExtra("deskripsi",hasilResponse.deskripsi_wisata)
            }
            holder.imgWisata.context.startActivity(intent)
        }
    }

    fun setData(data: List<wisataResponse.ListItems>){
        dataWisata.clear()
        dataWisata.addAll(data)
    }
}