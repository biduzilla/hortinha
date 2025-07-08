package com.ricky.repository.impl

import com.ricky.db.UsuarioDAO
import com.ricky.db.UsuarioTable
import com.ricky.db.suspendTransaction
import com.ricky.models.Page
import com.ricky.models.Usuario
import com.ricky.repository.UsuarioRepository
import com.ricky.utils.getLastPage
import com.ricky.utils.getOffset
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import java.util.*

class IUsuarioRepository : UsuarioRepository {
    override suspend fun getAll(pageSize: Int, page: Int, sort: String?): Page<Usuario> {
      return suspendTransaction {
          val offset = getOffset(page, pageSize)
          val query = UsuarioDAO.all()
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
            UsuarioDAO.new {
                nome = usuario.nome
                email = usuario.email
                senha = usuario.senha
                codVerificacao = usuario.codVerificacao
            }.toUsuario()
        }
    }

    override suspend fun deleteById(idUsuario: UUID) {
        suspendTransaction {
            val rowDeleted = UsuarioTable.deleteWhere {
                UsuarioTable.id eq idUsuario
            }
            rowDeleted == 1
        }
    }
}