package android.imd.dreamcall.Model

import java.util.Calendar

enum class DiarioState(val state: String){
    Noite("Noite"),
    Dia("Dia"),
    Registro("Registro")

//    override fun toString(): String {
//        return super.toString()
//    }
}

data class DiarioSono(val id: String, val date: String, var state: String, var bedtime: String, var waketime: String)