package android.imd.dreamcall

import android.app.TimePickerDialog
import android.imd.dreamcall.Model.DiarioState
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_diario_sono.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*


/* TODO update: Incluir bedtime e waketime em formato de data, para que seja possível calcular diferença de horas */
class DiarioSonoActivity : AppCompatActivity() {

    private var state: DiarioState = DiarioState.Noite
    private lateinit var date: String

    private lateinit var btn_bedtime: Button
    private lateinit var btn_waketime: Button
    private lateinit var btn_salvar: Button
    private lateinit var txt_state: TextView
    private lateinit var txt_date: TextView

    private var bedtime = Calendar.getInstance()
    private var waketime = Calendar.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario_sono)

        txt_state = findViewById(R.id.txt_state)
        txt_date = findViewById(R.id.txt_date)

        btn_bedtime = findViewById(R.id.btn_bedtime)
        btn_waketime = findViewById(R.id.btn_waketime)
        btn_salvar = findViewById(R.id.btn_salvar)


        /* Executa se a tela tiver sido criada agora */
        if(btn_bedtime.text == "--:--"){
            val cal = Calendar.getInstance()
            date = SimpleDateFormat("dd/MM").format(cal.time)
            txt_date.setText(date)
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
            bedtime = cal
        }

        btn_waketime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btn_waketime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            waketime = cal
        }

        btn_salvar.setOnClickListener {
            if(state == DiarioState.Noite && btn_bedtime.text != "--:--"){
                this.state = DiarioState.Dia
                updateState()

                /*var intent = Intent(applicationContext, ListActivity::class.java)
                startActivity(intent)
                finish()*/
            } else if(state == DiarioState.Dia && btn_waketime.text != "--:--"){
                this.state = DiarioState.Registro
                updateState()

                /*var intent = Intent(applicationContext, ListActivity::class.java)
                startActivity(intent)
                finish()*/
            } else {
                Toast.makeText(this, "Horário não preenchido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /* Habilita/desabilita botões de acordo com estado de registro da noite */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateState(){
        txt_state.text = state.toString()

        if(state == DiarioState.Noite){
            disableButton(btn_waketime)
        } else if(state == DiarioState.Dia){
            disableButton(btn_bedtime)
            enableButton(btn_waketime)
        } else if(state == DiarioState.Registro){
            disableButton(btn_bedtime)
            disableButton(btn_waketime)
            disableButton(btn_salvar)
            sleepTime()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun sleepTime(){
        val txtSleeptime = findViewById<TextView>(R.id.txt_sleeptime)
        txtSleeptime.isVisible = true

        var sleeptime = ChronoUnit.HOURS.between(bedtime.toInstant(), waketime.toInstant())

        txtSleeptime.text = "$sleeptime horas de sono"
    }

}
