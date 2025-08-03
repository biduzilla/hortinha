package com.ricky.dto

import com.ricky.models.Usuario
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UsuarioSaveDTO(
    var nome: String = "",
    var email: String = "",
    var senha: String = ""
) {
    fun toModel(): Usuario = Usuario(
        idUsuario = UUID.randomUUID(),
        nome = nome,
        email = email
    )
}
