package roni.putra.uasmi2b.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import roni.putra.uasmi2b.model.tambahWisataResponse
import roni.putra.uasmi2b.model.wisataResponse


interface ApiService {
    @GET("wisata/get_wisata.php")
    fun getListWisata(@Query("nama_wisata") nama: String): Call<wisataResponse>

    @Multipart
    @POST("wisata/add_wisata.php")
    fun addWisata(
        @Part("nama_wisata") nama: RequestBody,
        @Part("notlp") notlp: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("deskripsi_wisata") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part
    ): Call<tambahWisataResponse>


}