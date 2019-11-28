package android.imd.dreamcall

import android.content.Intent
import android.imd.dreamcall.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class ListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java)) // Redireciona caso não esteja logado
            finish()
        }

    }

    fun logout(view: View){
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
