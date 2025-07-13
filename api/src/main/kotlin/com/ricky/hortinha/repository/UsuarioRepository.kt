package com.ricky.hortinha.repository

import com.ricky.hortinha.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Usuario?

    @Query("select count(u) > 0 from Usuario u where u.email = :email")
    fun existsByEmail(@Param("email") email: String): Boolean

    @Query("select count(u) > 0 from Usuario u where u.codVerificacao = :codVerificacao")
    fun existsByCodVerificacao(@Param("codVerificacao") codVerificacao: Int): Boolean

    @Query("select u from Usuario u where u.codVerificacao = :codVerificacao")
    fun findByCodVerificacao(@Param("codVerificacao") codVerificacao: Int): Usuario?

    @Query(
        """
        select u from Usuario u 
            where u.codVerificacao = :codVerificacao
            and u.email = :email
    """
    )
    fun findByEmailAndCodVerificacao(
        @Param("email") email: String,
        @Param("codVerificacao") codVerificacao: Int
    ): Usuario?

    @Query(
        """
        select count(u) > 0 from Usuario u 
            where u.codVerificacao = :codVerificacao
            and u.email = :email
    """
    )
    fun existsByEmailAndCodVerificacao(
        @Param("email") email: String,
        @Param("codVerificacao") codVerificacao: Int
    ): Boolean
}