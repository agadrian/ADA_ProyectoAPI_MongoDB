package com.es.apirest_mongodb.dto

data class DireccionDTO(
    val calle: String,
    val num: String,
    val municipio: String,
    val provincia: String,
    val cp: String
)