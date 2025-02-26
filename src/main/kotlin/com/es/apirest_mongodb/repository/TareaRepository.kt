package com.es.apirest_mongodb.repository

import com.es.apirest_mongodb.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository

interface TareaRepository : MongoRepository<Tarea, String> {

    fun findByUsuarioId(userId: String): List<Tarea>
}