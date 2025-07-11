package com.ricky.hortinha.repository

import com.ricky.hortinha.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Usuario?
}