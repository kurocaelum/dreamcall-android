package android.imd.dreamcall

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

class DiarioSonoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario_sono)

        val btn_bedtime = findViewById<Button>(R.id.btn_bedtime)
        val btn_waketime = findViewById<Button>(R.id.btn_waketime)

        btn_bedtime.setOnClickListener {
            makeToast("bedtime")
            disableButton(btn_bedtime)
        }

        btn_waketime.setOnClickListener {
            makeToast("waketime")
            disableButton(btn_waketime)
        }
    }

    fun makeToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun disableButton(button: Button){
        button.isClickable = false
        ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, android.R.color.darker_gray))
    }

}
