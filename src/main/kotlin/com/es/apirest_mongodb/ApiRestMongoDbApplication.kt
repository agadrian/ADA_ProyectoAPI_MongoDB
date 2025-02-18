package com.es.apirest_mongodb

import com.es.apirest_mongodb.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class ApiRestMongoDbApplication

fun main(args: Array<String>) {
	runApplication<ApiRestMongoDbApplication>(*args)
}
