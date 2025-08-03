package com.ricky.validation

import com.ricky.dto.UsuarioDTO
import com.ricky.dto.UsuarioSaveDTO
import io.ktor.server.plugins.requestvalidation.*


fun RequestValidationConfig.userValidation() {
    validate<UsuarioDTO> { user ->
        val violations = buildList {
            user.apply {
                when {
                    nome.isBlank() -> add("Nome de usuário é obrigatório")
                    nome.length < 3 -> add("Nome deve ter pelo menos 3 caracteres")
                    nome.length > 50 -> add("Nome não pode exceder 50 caracteres")
                    !nome.matches(Regex("^[a-zA-Z0-9._-]+$")) ->
                        add("Nome só pode conter letras, números, pontos, hífens e underscores")
                }

                when {
                    senha.isBlank() -> add("Senha é obrigatória")
                    senha.length < 8 -> add("Senha deve ter pelo menos 8 caracteres")
                    senha.length > 128 -> add("Senha não pode exceder 128 caracteres")
                    !senha.any(Char::isDigit) ->
                        add("Senha deve conter pelo menos 1 número")

                    !senha.any(Char::isUpperCase) ->
                        add("Senha deve conter pelo menos 1 letra maiúscula")

                    !senha.any { it in "!@#$%^&*()_+" } ->
                        add("Senha deve conter pelo menos 1 caractere especial")
                }

                when {
                    email.isBlank() -> add("Email é obrigatório")
                    email.length > 254 -> add("Email não pode exceder 254 caracteres")
                    !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")) ->
                        add("Formato de email inválido")

                    !email.split("@").last().matches(Regex("^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) ->
                        add("Domínio de email inválido")

                    email.contains("..") || email.contains(".@") || email.contains("@.") ->
                        add("Email contém sequências inválidas")
                }

            }

        }

        violations.takeIf { it.isNotEmpty() }?.let {
            ValidationResult.Invalid(it.joinToString(separator = " | "))
        } ?: ValidationResult.Valid
    }
    validate<UsuarioSaveDTO> { user ->
        val violations = buildList {
            user.apply {
                when {
                    nome.isBlank() -> add("Nome de usuário é obrigatório")
                    nome.length < 3 -> add("Nome deve ter pelo menos 3 caracteres")
                    nome.length > 50 -> add("Nome não pode exceder 50 caracteres")
                    !nome.matches(Regex("^[a-zA-Z0-9._-]+$")) ->
                        add("Nome só pode conter letras, números, pontos, hífens e underscores")
                }

                when {
                    senha.isBlank() -> add("Senha é obrigatória")
                    senha.length < 8 -> add("Senha deve ter pelo menos 8 caracteres")
                    senha.length > 128 -> add("Senha não pode exceder 128 caracteres")
                    !senha.any(Char::isDigit) ->
                        add("Senha deve conter pelo menos 1 número")

                    !senha.any(Char::isUpperCase) ->
                        add("Senha deve conter pelo menos 1 letra maiúscula")

                    !senha.any { it in "!@#$%^&*()_+" } ->
                        add("Senha deve conter pelo menos 1 caractere especial")
                }

                when {
                    email.isBlank() -> add("Email é obrigatório")
                    email.length > 254 -> add("Email não pode exceder 254 caracteres")
                    !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")) ->
                        add("Formato de email inválido")

                    !email.split("@").last().matches(Regex("^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) ->
                        add("Domínio de email inválido")

                    email.contains("..") || email.contains(".@") || email.contains("@.") ->
                        add("Email contém sequências inválidas")
                }

            }

        }

        violations.takeIf { it.isNotEmpty() }?.let {
            ValidationResult.Invalid(it.joinToString(separator = " | "))
        } ?: ValidationResult.Valid
    }
}