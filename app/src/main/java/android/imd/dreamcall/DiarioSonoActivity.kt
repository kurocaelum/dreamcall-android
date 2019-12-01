package android.imd.dreamcall

import android.app.TimePickerDialog
import android.graphics.Color
import android.imd.dreamcall.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_splash.*
import java.text.SimpleDateFormat
import java.util.*


class DiarioSonoActivity : AppCompatActivity() {

    enum class State{
        Noite,
        Dia,
        Registro;

        override fun toString(): String {
            return super.toString()
        }
    }

    private var state: State? = null
    private lateinit var date: String
    private lateinit var btn_bedtime: Button
    private lateinit var btn_waketime: Button
    private lateinit var btn_preencher: Button
    private lateinit var txt_state: TextView
    private lateinit var txt_date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario_sono)

        txt_state = findViewById(R.id.txt_state)
        txt_date = findViewById(R.id.txt_date)

        btn_bedtime = findViewById(R.id.btn_bedtime)
        btn_waketime = findViewById(R.id.btn_waketime)
        btn_preencher = findViewById(R.id.btn_preencher)

        if(state == null){
            val cal = Calendar.getInstance()
            date = SimpleDateFormat("dd/MM").format(cal.time)
            txt_date.setText(date)

            state = State.Noite
        }

        updateState()

        btn_bedtime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btn_bedtime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btn_waketime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btn_waketime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btn_preencher.setOnClickListener {
            if(state == State.Noite && btn_bedtime.text != "--:--"){
                this.state = State.Dia
                updateState()
            } else if(state == State.Dia && btn_waketime.text != "--:--"){
                this.state = State.Registro
                updateState()
            } else {
                Toast.makeText(this, "Horário não preenchido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /* Habilita/desabilita botões de acordo com estado de registro da noite */
    private fun updateState(){
        txt_state.text = state.toString()

        if(state == State.Noite){
            disableButton(btn_waketime)
        } else if(state == State.Dia){
            disableButton(btn_bedtime)
            enableButton(btn_waketime)
        } else if(state == State.Registro){
            disableButton(btn_bedtime)
            disableButton(btn_waketime)
            disableButton(btn_preencher)
        }
    }

    fun disableButton(button: Button){
        button.isEnabled = false
        ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, android.R.color.darker_gray))
    }

    fun enableButton(button: Button){
        button.isEnabled = true
        ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, R.color.colorAccent))
    }

}
