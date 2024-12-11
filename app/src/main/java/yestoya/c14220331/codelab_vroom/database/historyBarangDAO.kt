package yestoya.c14220331.codelab_vroom.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBarangDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBarang)

    @Query("UPDATE historyBarang SET tanggal=:isi_tanggal, item=:isi_item, status=:isi_status, jumlah=:isi_jumlah WHERE id=:pilihid")
    fun update(isi_tanggal: String, isi_item: String, isi_jumlah: String, isi_status:Int, pilihid: Int)

    @Delete
    fun delete(history: historyBarang)

    @Query("SELECT * FROM historyBarang ORDER BY id asc")
    fun selectAll(): List<historyBarang>

    @Query("SELECT * FROM historyBarang WHERE id=:isi_id")
    suspend fun getItem(isi_id : Int) : historyBarang




}