package com.ricky.hortinha.dto

import jakarta.validation.constraints.NotBlank

data class UsuarioDTO(
    var id: String = "",
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    var senha: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    var email: String = "",
)
