package com.ricky.hortinha.dto

import com.ricky.hortinha.models.Usuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioDTO(
    var idUsuario: String = "",
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",
) {
    fun toModel(): Usuario {
        return Usuario(
            idUsuario = idUsuario,
            nome = nome,
            email = email
        )
    }
}
