package yestoya.c14220331.codelab_vroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yestoya.c14220331.codelab_vroom.database.daftarBelanja

class adapterRecView (private val daftarBelanja : MutableList<daftarBelanja>):
    RecyclerView.Adapter<adapterRecView.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var TV_item = itemView.findViewById<TextView>(R.id.TV_item)
        var TV_tanggal = itemView.findViewById<TextView>(R.id.TV_tanggal)
        var TV_jumlah = itemView.findViewById<TextView>(R.id.TV_jumlah)

        var _btnEdit = itemView.findViewById<ImageButton>(R.id.editBTN)
        var _btnDelete = itemView.findViewById<ImageButton>(R.id.delBTN)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterRecView.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recycler, parent, false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }


    override fun onBindViewHolder(holder: adapterRecView.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]

        holder.TV_tanggal.text = daftar.tanggal
        holder.TV_item.text = daftar.item
        holder.TV_jumlah.text = daftar.jumlah

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

    }

    fun isiData(daftar: List<daftarBelanja> ){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

}