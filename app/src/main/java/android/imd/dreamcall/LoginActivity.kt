package android.imd.dreamcall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        val pref = getSharedPreferences("configuracoes", 0)
        edtEmailLogin.setText(pref.getString("email", ""))
    }

    fun cadastrar(view: View){
        val intent = Intent(applicationContext, CadastroActivity::class.java)
        startActivity(intent)
    }

    fun entrar(view: View){
        auth.signInWithEmailAndPassword(edtEmailLogin.text.toString(), edtSenhaLogin.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val pref = getSharedPreferences("configuracoes", 0)
                    val edit = pref.edit()
                    edit.putString("email", edtEmailLogin.text.toString())
                    edit.apply()

                    Toast.makeText(this@LoginActivity,
                        "Usuário logado com sucesso", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity, ListActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity,
                        "Autenticação falhou", Toast.LENGTH_LONG).show()
                }
            }
    }

}
