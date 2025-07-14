package com.ricky.hortinha.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioDTO(
    var id: String = "",
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",
)
