package android.imd.dreamcall

import android.content.Intent
import android.imd.dreamcall.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_logout){
            logout()
            return true
        } else if(id == R.id.action_fill){
            startActivity(Intent(this, DiarioSonoActivity::class.java))
            return true

            /*Toast.makeText(this, "TO DO Preencher", Toast.LENGTH_SHORT).show() // TODO
            return true*/
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun logout(){
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
