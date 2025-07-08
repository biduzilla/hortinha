package com.ricky.repository

import com.ricky.models.Page
import com.ricky.models.Usuario
import java.util.*

interface UsuarioRepository {
    suspend fun getAll(pageSize: Int = 10, page: Int = 0, sort: String?): Page<Usuario>
    suspend fun getById(idUsuario: UUID): Usuario?
    suspend fun save(usuario: Usuario): Usuario
    suspend fun deleteById(idUsuario: UUID)
}