package com.ricky.module

import com.ricky.repository.UsuarioRepository
import com.ricky.repository.impl.UsuarioRepositoryImpl
import com.ricky.service.UsuarioService
import com.ricky.service.impl.UsuarioServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::UsuarioRepositoryImpl) { bind<UsuarioRepository>() }
    singleOf(::UsuarioServiceImpl) { bind<UsuarioService>() }
}
