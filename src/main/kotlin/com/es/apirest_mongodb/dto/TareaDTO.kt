package com.es.apirest_mongodb.dto

data class TareaDTO(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val estado: String,  // PENDIENTE - COMPLETADA
    val usuarioId: String  // ID del usuario propietario
)
