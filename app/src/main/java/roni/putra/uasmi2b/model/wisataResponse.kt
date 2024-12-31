package roni.putra.uasmi2b.model

data class wisataResponse(
    val success: Boolean,
    val message : String,
    val data : ArrayList<ListItems>
){
    data class ListItems(
        val id: String,
        val nama_wisata: String,
        val notlp: String,
        val alamat: String,
        val deskripsi_wisata: String,
        val gambar: String,
    )
}
