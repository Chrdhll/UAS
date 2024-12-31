package roni.putra.uasmi2b

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailWisataActivity : AppCompatActivity() {
    private lateinit var imgWisata : ImageView
    private lateinit var tvNama : TextView
    private lateinit var tvNoTlp : TextView
    private lateinit var tvAlamat: TextView
    private lateinit var tvDeskripsi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_wisata)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgWisata = findViewById(R.id.imgWisata)
        tvNama = findViewById(R.id.tvNama)
        tvNoTlp = findViewById(R.id.tvNoTlp)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvDeskripsi = findViewById(R.id.tvDeskripsi)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgWisata)
        tvNama.text = intent.getStringExtra("nama")
        tvNoTlp.text = intent.getStringExtra("notlp")
        tvAlamat.text = intent.getStringExtra("alamat")
        tvDeskripsi.text = intent.getStringExtra("deskripsi")
    }
}