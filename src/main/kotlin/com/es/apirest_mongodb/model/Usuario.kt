package com.es.apirest_mongodb.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("Usuarios")
data class Usuario(

    @BsonId
    val id : String?,

    @Indexed(unique = true)
    val username: String,

    val password: String,

    @Indexed(unique = true)
    val email: String,

    val roles: String? = "USER",

    val direccion: Direccion?

)