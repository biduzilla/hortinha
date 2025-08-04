package com.ricky.service.impl

import com.ricky.exceptions.GenericServerError
import com.ricky.models.Page
import com.ricky.models.Usuario
import com.ricky.repository.UsuarioRepository
import com.ricky.service.UsuarioService
import io.ktor.http.*
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.ktor.plugin.koinModule
import java.util.*

class UsuarioServiceImpl(private val usuarioRepository: UsuarioRepository) : UsuarioService {
    fun Application.usuarioServiceModule() {
        koinModule {
            singleOf(::UsuarioServiceImpl) bind UsuarioService::class
        }
    }

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

    override suspend fun update(usuario: Usuario): Usuario {
        val user = getById(usuario.idUsuario)
        user.nome = usuario.nome
        user.email = usuario.email
        return usuarioRepository.update(usuario) ?: throw GenericServerError(
            statusCode = HttpStatusCode.NotFound.value,
            errorMessage = "Usuário com ID ${usuario.idUsuario} não encontrado",
            httpStatus = HttpStatusCode.NotFound.description
        )
    }

    override suspend fun deleteById(idUsuario: UUID) {
        usuarioRepository.deleteById(idUsuario)
    }
}