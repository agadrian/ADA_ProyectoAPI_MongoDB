package com.es.apirest_mongodb.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tareas")
data class Tarea(
    @BsonId
    val id: String? = null,

    val titulo: String,

    val descripcion: String,

    val estado: String = "PENDIENTE",  // PENDIENTE - COMPLETADA

    val usuarioId: String  // ID del usuario propietario
)
