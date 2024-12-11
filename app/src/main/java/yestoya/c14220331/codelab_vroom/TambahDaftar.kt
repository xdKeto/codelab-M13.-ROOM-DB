package yestoya.c14220331.codelab_vroom

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import yestoya.c14220331.codelab_vroom.database.daftarBelanja
import yestoya.c14220331.codelab_vroom.database.daftarBelanjaDB
import yestoya.c14220331.codelab_vroom.helper.DateHelper.getCurrentDate

class TambahDaftar : AppCompatActivity() {

    var DB = daftarBelanjaDB.getDatabase(this)
    var iID : Int = 0
    var iAddEdit : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var tanggal = getCurrentDate()

        val btnTambah = findViewById<Button>(R.id.addBTN)
        val btnUpdate = findViewById<Button>(R.id.updateBTN)
        val etItem = findViewById<EditText>(R.id.ET_item)
        val etJumlah = findViewById<EditText>(R.id.ET_jumlah)

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funDaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal = tanggal,
                        item = etItem.text.toString(),
                        jumlah = etJumlah.text.toString(),

                    )
                )
            }
        }

        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            btnTambah.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
            etItem.isEnabled = true
        } else {
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            etItem.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.funDaftarBelanjaDAO().getItem(iID)
                etItem.setText(item.item)
                etJumlah.setText(item.jumlah)
            }
        }

        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funDaftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = etItem.text.toString(),
                    isi_jumlah = etJumlah.text.toString(),
                    isi_status = 0,
                    pilihid = iID
                )
            }
        }


    }
}
