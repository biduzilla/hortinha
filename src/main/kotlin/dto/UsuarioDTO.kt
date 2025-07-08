package com.ricky.dto

import com.ricky.models.Usuario
import java.util.*

data class UsuarioDTO(
    val idUsuario: UUID,
    var nome: String = "",
    var email: String = "",
) {
    fun toModel(): Usuario = Usuario(
        idUsuario = idUsuario,
        nome = nome,
        email = email
    )
}
