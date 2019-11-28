package android.imd.dreamcall

import android.content.Intent
import android.imd.dreamcall.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        auth = FirebaseAuth.getInstance()

        btnCadastrarCadastro.setOnClickListener { salvarCadastro() }
    }

    fun salvarCadastro(){
        if(!Patterns.EMAIL_ADDRESS.matcher(edtEmailCadastro.text.toString()).matches()) {
            edtEmailCadastro.error = "E-mail inválido"
            return
        }

        if(edtEmailCadastro.text.isEmpty()){
            edtEmailCadastro.error = "Campo E-mail Obrigatório"
        }else if (edtSenhaCadastro.text.isEmpty()){
            edtSenhaCadastro.error = "Campo Senha Obrigatório"
        }else{
            auth.createUserWithEmailAndPassword(edtEmailCadastro.text.toString(),edtSenhaCadastro.text.toString())
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@CadastroActivity,
                            "Usuário Cadastrado com Sucesso", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, ListActivity::class.java))
                        finish()
                    }else{
                        val resposta = task.exception!!.toString()
                        Toast.makeText(this@CadastroActivity,
                            resposta, Toast.LENGTH_LONG).show()
                    }

                }
        }
    }
}
