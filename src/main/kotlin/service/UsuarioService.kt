package com.ricky.service

import com.ricky.models.Page
import com.ricky.models.Usuario
import java.util.*

interface UsuarioService {
    fun getAll(): Page<Usuario>
    fun getById(idUsuario: UUID): Usuario?
    fun save(usuario: Usuario): Usuario
    fun deleteById(idUsuario: UUID)
}