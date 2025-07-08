package com.ricky.service

import com.ricky.exceptions.GenericServerError
import com.ricky.models.Page
import com.ricky.models.Usuario
import com.ricky.repository.UsuarioRepository
import io.ktor.http.*
import java.util.*

class IUsuarioService(
    private val usuarioRepository: UsuarioRepository
) : UsuarioService {
    override suspend fun getAll(pageSize: Int, page: Int): Page<Usuario> {
        return usuarioRepository.getAll(
            pageSize,
            page
        )
    }

    override suspend fun getById(idUsuario: UUID): Usuario {
        return usuarioRepository.getById(idUsuario) ?: throw GenericServerError(
            statusCode = HttpStatusCode.NotFound.value,
            errorMessage = "Usuário com ID $idUsuario não encontrado",
            httpStatus = HttpStatusCode.NotFound.description
        )
    }

    override suspend fun save(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    override suspend fun deleteById(idUsuario: UUID) {
        usuarioRepository.deleteById(idUsuario)
    }
}