package com.ricky.service

import com.ricky.models.Page
import com.ricky.models.Usuario
import java.util.*

interface UsuarioService {
    suspend fun getAll(pageSize: Int = 10, page: Int = 0): Page<Usuario>
    suspend fun getById(idUsuario: UUID): Usuario
    suspend fun save(usuario: Usuario): Usuario
    suspend fun deleteById(idUsuario: UUID)
}