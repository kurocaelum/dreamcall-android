package android.imd.dreamcall

import android.imd.dreamcall.Model.DiarioSono
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_diario.view.*

class DiarioSonoAdapter(
    private val diarios: List<DiarioSono>,
    private val callback: (DiarioSono)-> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = DiarioSonoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_diario, parent, false)
        )

        // TODO escrever aqui mesmo
        vh.itemView.setOnClickListener{
            val diario = diarios[vh.adapterPosition]
            callback(diario)
        }

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DiarioSonoViewHolder ->{ holder.bind(diarios.get(position)) }
        }
    }

    override fun getItemCount(): Int = diarios.size

    class DiarioSonoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtDate: TextView = itemView.txt_date_list
        val txtState: TextView = itemView.txt_state_list
//        val txtBedtime: TextView = itemView.txt_bedtime_list
//        val txtWaketime: TextView = itemView.txt_waketime_list

        fun bind(diarioSono: DiarioSono){
            txtDate.text = diarioSono.date
            txtState.text = diarioSono.state.toString()
        }
    }
}