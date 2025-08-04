package com.ricky.repository.impl

import com.ricky.db.UsuarioDAO
import com.ricky.db.UsuarioTable
import com.ricky.db.suspendTransaction
import com.ricky.models.Page
import com.ricky.models.Usuario
import com.ricky.repository.UsuarioRepository
import com.ricky.utils.getLastPage
import com.ricky.utils.getOffset
import io.ktor.server.application.*
import org.jetbrains.exposed.dao.flushCache
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.ktor.plugin.koinModule
import java.util.*

class UsuarioRepositoryImpl : UsuarioRepository {
    fun Application.usuarioDataModule() {
        koinModule {
            singleOf(::UsuarioRepositoryImpl) bind UsuarioRepository::class
        }
    }

    override suspend fun getAll(pageSize: Int, page: Int): Page<Usuario> {
        return suspendTransaction {
            val offset = getOffset(page, pageSize)
            val query = UsuarioDAO.all()
                .orderBy(UsuarioTable.createdAt to SortOrder.DESC)
                .limit(pageSize)
                .offset(offset)
            val totalRecords = UsuarioDAO.count()
            val lastPage = getLastPage(totalRecords, pageSize)
            val content = query.map { it.toUsuario() }

            Page(
                currentPage = page,
                pageSize = pageSize,
                firstPage = 1,
                lastPage = lastPage,
                totalRecords = totalRecords.toInt(),
                content = content
            )
        }
    }


    override suspend fun getById(idUsuario: UUID): Usuario? {
        return suspendTransaction {
            UsuarioDAO
                .find { UsuarioTable.id eq idUsuario }
                .limit(1)
                .map { it.toUsuario() }
                .firstOrNull()
        }
    }

    override suspend fun save(usuario: Usuario): Usuario {

        return suspendTransaction {
            val userSave = UsuarioDAO.new {
                nome = usuario.nome
                email = usuario.email
                senha = usuario.senha
            }
            flushCache()
            userSave.toUsuario()
        }
    }

    override suspend fun update(usuario: Usuario): Usuario? {
        return suspendTransaction {
            val userUpdated = UsuarioDAO.findByIdAndUpdate(usuario.idUsuario) { dao ->
                dao.nome = usuario.nome
                dao.email = usuario.email
                dao.senha = usuario.senha
                dao.codVerificacao = usuario.codVerificacao
            }

            flushCache()
            userUpdated?.toUsuario()
        }
    }

    override suspend fun deleteById(idUsuario: UUID) {
        suspendTransaction {
            val rowDeleted = UsuarioTable.deleteWhere {
                UsuarioTable.id eq idUsuario
            }
            flushCache()
            rowDeleted == 1

        }
    }
}