package com.ricky.hortinha.controller

import com.ricky.hortinha.dto.ResetSenhaDTO
import com.ricky.hortinha.dto.UsuarioDTO
import com.ricky.hortinha.dto.UsuarioSaveDTO
import com.ricky.hortinha.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Tag(
    name = "Usuário",
    description = "Operações relacionadas ao gerenciamento de usuários"
)
class UsuarioController(
    private val usuarioService: UsuarioService
) {
    @Operation(
        summary = "Criar novo usuário",
        description = "API para criar um novo usuário no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@Valid @RequestBody data: UsuarioSaveDTO): UsuarioDTO {
        return usuarioService.save(data.toModel()).toDTO()
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "API para buscar um usuário específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): UsuarioDTO {
        return usuarioService.findById(id).toDTO()
    }

    @PutMapping
    @Operation(
        summary = "Atualizar usuário",
        description = "API para atualizar os dados de um usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        ]
    )
    fun update(@RequestBody @Valid usuarioDTO: UsuarioDTO): ResponseEntity<UsuarioDTO?> {
        val usuario = usuarioService.update(usuarioDTO.toModel())
        return ResponseEntity.status(HttpStatus.OK).body(usuario.toDTO())
    }

    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Excluir usuário",
        description = "API para excluir um usuário pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        ]
    )
    fun deleteById(@PathVariable idUsuario: String) {
        usuarioService.deleteById(idUsuario)
    }

    @Operation(
        summary = "Verificar código de verificação",
        description = "API para verificar o código de verificação enviado para o usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Código verificado com sucesso"),
        ]
    )
    @GetMapping("verificar-cod/{cod}/{email}")
    fun verificarCod(
        @PathVariable cod: Int,
        @PathVariable email: String,
    ) {
        usuarioService.verificarCod(
            cod = cod,
            email = email
        )
    }

    @PutMapping("/reset-senha/{email}")
    @Operation(
        summary = "Enviar e-mail para resetar senha",
        description = "API para gerar um código de verificação e enviar um e-mail para resetar a senha."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
        ]
    )
    fun enviarEmailSenha(@PathVariable email: String) {
        usuarioService.enviarEmailSenha(email)
    }

    @PutMapping("/alterar-senha")
    @Operation(
        summary = "Alterar senha do usuário",
        description = "API para alterar a senha do usuário com base em um código de verificação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        ]
    )
    fun alterarSenha(
        @RequestBody @Valid resetSenhaDTO: ResetSenhaDTO
    ) {
        usuarioService.alterarSenha(
            email = resetSenhaDTO.email,
            senha = resetSenhaDTO.senha,
            cod = resetSenhaDTO.cod
        )
    }

}