package android.imd.dreamcall

import android.content.Intent
import android.imd.dreamcall.Model.DiarioSono
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject

class ListActivity : AppCompatActivity() {

    companion object{
        private lateinit var auth: FirebaseAuth
        private lateinit var listView: ListView
        private var diarios = arrayListOf<DiarioSono>()
        val db = FirebaseFirestore.getInstance()
        val dbDiarios = db.collection("diarios-sono")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listView = findViewById(R.id.listView)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java)) // Redireciona caso não esteja logado
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        loadDiarios()
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
            var intent = Intent(this, DiarioSonoActivity::class.java)
            startActivity(intent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun logout(){
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun loadDiarios(){
        diarios.clear()

        dbDiarios.get().addOnSuccessListener { result->
            for(doc in result){
                var jo = JSONObject(doc.data)
                var diario = DiarioSono(
                    doc.id,
                    jo.getString("date"),
                    jo.getString("state"),
                    jo.getString("bedtime"),
                    jo.getString("waketime")
                )
                diarios.add(diario)
            }

            listView.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, diarios)
        }
        .addOnFailureListener{e->
            Toast.makeText(this, "Erro: $e",Toast.LENGTH_LONG).show()
        }
    }

}
