package roni.putra.uasmi2b

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.uasmi2b.api.ApiClient
import roni.putra.uasmi2b.model.tambahWisataResponse
import java.io.File

class TambahWisataActivity : AppCompatActivity() {
    private lateinit var etNama : EditText
    private lateinit var etNotlp : EditText
    private lateinit var etAlamat : EditText
    private lateinit var etDeskripsi : EditText
    private lateinit var btnGambar : Button
    private lateinit var imgGambar : ImageView
    private lateinit var progressBar : ProgressBar
    private lateinit var btnTambah : Button

    private var imageFIle : File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_wisata)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etNama = findViewById(R.id.etNama)
        etNotlp = findViewById(R.id.etNotlp)
        etAlamat = findViewById(R.id.etAlamat)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnTambah = findViewById(R.id.btnTambah)
        progressBar = findViewById(R.id.progressBar)

        btnGambar.setOnClickListener(){
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFIle?.let { file ->
                tambahWisata(etNama.text.toString(), etNotlp.text.toString(), etAlamat.text.toString(),etDeskripsi.text.toString(), file)
            }
        }

    }

    //menampilkan override method tekan tombol ctrl + o
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFIle = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)
        }
    }

    //proses tambah berita
    private fun tambahWisata(nama: String, notlp: String, alamat:String, deskripsi: String, gambar: File){
        progressBar.visibility = View.VISIBLE
        val requestBody = gambar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("gambar", gambar.name, requestBody)

        val nama = nama.toRequestBody("text/plain".toMediaTypeOrNull())
        val notlp = notlp.toRequestBody("text/plain".toMediaTypeOrNull())
        val alamat = alamat.toRequestBody("text/plain".toMediaTypeOrNull())
        val deskripsi = deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addWisata(nama, notlp, alamat, deskripsi, part)
            .enqueue(object : Callback<tambahWisataResponse> {
                override fun onResponse(
                    call: Call<tambahWisataResponse>,
                    response: Response<tambahWisataResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()!!.success){
                            startActivity(Intent(this@TambahWisataActivity,MainActivity::class.java))
                        }else{
                            Toast.makeText(this@TambahWisataActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@TambahWisataActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<tambahWisataResponse>, t: Throwable) {
                    Toast.makeText(this@TambahWisataActivity,t.message, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            })
    }
}