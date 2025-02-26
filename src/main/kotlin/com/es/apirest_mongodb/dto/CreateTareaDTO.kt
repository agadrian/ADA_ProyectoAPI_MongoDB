package com.es.apirest_mongodb.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateTareaDTO(
    val titulo: String,
    val descripcion: String
)
