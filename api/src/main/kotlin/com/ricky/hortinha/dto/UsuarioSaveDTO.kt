package com.ricky.hortinha.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UsuarioSaveDTO(
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(message = "{error.senha.curta}", min = 8)
    var senha: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",
)
