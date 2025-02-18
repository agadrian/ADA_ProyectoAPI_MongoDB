package com.es.apirest_mongodb.dto

data class UsuarioDTO(
    val username: String,
    val email: String,
    val rol: String?
)