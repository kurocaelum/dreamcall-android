package android.imd.dreamcall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
//    private var diarios = mutableListOf<DiarioSono>()
//    private var diarioAdapter = DiarioSonoAdapter(diarios, this::onDiarioItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java)) // Redireciona caso não esteja logado
            finish()
        }

//        initRecyclerView()
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

    /* TODO RECYCLER VIEW */

    /*private fun initRecyclerView(){
        rv_diarios.apply {
            adapter = diarioAdapter
            layoutManager = LinearLayoutManager(this@ListActivity)
        }
    }*/

    /*fun addDiarioSono(diario: DiarioSono){
        diarios.add(diario)
        diarioAdapter.notifyItemInserted(diarios.lastIndex)
    }*/

    //  TODO envia ao diario selecionado
    /*private fun onDiarioItemClick(diario: DiarioSono){

    }*/

}
