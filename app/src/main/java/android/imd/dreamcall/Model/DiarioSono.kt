package android.imd.dreamcall.Model

import java.util.Calendar

enum class DiarioState{
    Noite,
    Dia,
    Registro;

    override fun toString(): String {
        return super.toString()
    }
}

data class DiarioSono(val date: String, var state: DiarioState, var bedtime: Calendar, var waketime: Calendar)