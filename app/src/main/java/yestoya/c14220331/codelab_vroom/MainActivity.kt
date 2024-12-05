package yestoya.c14220331.codelab_vroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import yestoya.c14220331.codelab_vroom.database.daftarBelanja
import yestoya.c14220331.codelab_vroom.database.daftarBelanjaDB

class MainActivity : AppCompatActivity() {
    private lateinit var DB: daftarBelanjaDB

    private lateinit var adapterDaftar : adapterRecView

    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapterDaftar = adapterRecView(arDaftar)
        var rvDaftar = findViewById<RecyclerView>(R.id.rvNotes)
        rvDaftar.layoutManager = LinearLayoutManager(this)
        rvDaftar.adapter = adapterDaftar

        DB = daftarBelanjaDB.getDatabase(this)

        val addBTN = findViewById<FloatingActionButton>(R.id.addBTN)
        addBTN.setOnClickListener {
            val intent = Intent(this, TambahDaftar::class.java)
            startActivity(intent)
        }

        adapterDaftar.setOnItemClickCallback(
            object : adapterRecView.OnItemClickCallback {
                override fun delData(dtBelanja: daftarBelanja){
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funDaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = DB.funDaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main){
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }
            }
        )

        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.funDaftarBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterDaftar.isiData(daftarBelanja)
        }

    }
}