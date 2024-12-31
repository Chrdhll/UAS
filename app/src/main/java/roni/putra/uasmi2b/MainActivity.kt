package roni.putra.uasmi2b

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.uasmi2b.adapter.wisataAdapter
import roni.putra.uasmi2b.api.ApiClient
import roni.putra.uasmi2b.model.wisataResponse

class MainActivity : AppCompatActivity() {
    private lateinit var svJudul : SearchView
    private lateinit var progressBar : ProgressBar
    private lateinit var rvWisata : RecyclerView
    private lateinit var floatBtnTambah : FloatingActionButton
    private lateinit var wisataAdapter: wisataAdapter
    private lateinit var imgNotFound : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        svJudul = findViewById(R.id.svJudul)
        progressBar = findViewById(R.id.progressBar)
        rvWisata = findViewById(R.id.rvWisata)
        floatBtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)


        //panggil method getBerita
        getWisata("")



        svJudul.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pencarian: String?): Boolean {
                getWisata(pencarian.toString())
                return true
            }
        })

        floatBtnTambah.setOnClickListener(){
            startActivity(Intent(this,TambahWisataActivity::class.java))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getWisata(nama: String){
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.getListWisata(nama).enqueue(object : Callback<wisataResponse> {
            override fun onResponse(call:
                                    Call<wisataResponse>,
                                    response: Response<wisataResponse>
            )
            {
                if (response.isSuccessful){
                    if (response.body()!!.success){
                        //set dta
                        wisataAdapter = wisataAdapter(arrayListOf())
                        rvWisata.adapter = wisataAdapter
                        wisataAdapter.setData(response.body()!!.data)
                        imgNotFound.visibility = View.GONE
                    }
                    else {
                        //jika data tidak ditemukan
                        wisataAdapter = wisataAdapter(arrayListOf())
                        rvWisata.adapter = wisataAdapter
                        imgNotFound.visibility = View.VISIBLE
                    }
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<wisataResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error : ${t.message}", Toast.LENGTH_LONG)
                    .show()
                progressBar.visibility = View.GONE
            }
        })
    }
}