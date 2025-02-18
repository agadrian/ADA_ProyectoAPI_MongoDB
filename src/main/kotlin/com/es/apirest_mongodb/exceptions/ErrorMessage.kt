package com.es.apirest_mongodb.exceptions

data class ErrorMessage(
    val status: Int,
    val message: String,
    val path: String
)