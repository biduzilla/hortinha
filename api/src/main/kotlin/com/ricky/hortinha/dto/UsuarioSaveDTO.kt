package com.ricky.hortinha.dto

import com.ricky.hortinha.models.Usuario
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UsuarioSaveDTO(
    @field:NotBlank(message = "{nome.obrigatorio}")
    @Schema(
        description = "E-mail do usuário para recuperação de senha",
        example = "usuario@exemplo.com",
        required = true
    )
    var nome: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(message = "{error.senha.curta}", min = 8)
    @Schema(
        description = "Nova senha do usuário, deve ter no mínimo 8 caracteres",
        example = "novaSenha123",
        required = true
    )
    var senha: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",
) {
    fun toModel(): Usuario {
        return Usuario(
            nome = nome,
            senha = senha,
            email = email
        )
    }
}
