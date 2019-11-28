package android.imd.dreamcall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread{
            SystemClock.sleep(3000)
            startActivity(Intent(applicationContext, ListActivity::class.java))
            finish()
        }.start()
    }
}
