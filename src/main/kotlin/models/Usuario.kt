package com.ricky.models

import com.ricky.dto.UsuarioDTO
import java.util.*

data class Usuario(
    val idUsuario: UUID,
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var codVerificacao: Int = 0,
) : BaseModel() {
    fun toDTO(): UsuarioDTO = UsuarioDTO(
        idUsuario = idUsuario,
        nome = nome,
        email = email
    )
}
