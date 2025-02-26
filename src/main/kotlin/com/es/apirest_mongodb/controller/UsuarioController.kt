package com.es.apirest_mongodb.controller

import com.es.apirest_mongodb.dto.LoginUsuarioDTO
import com.es.apirest_mongodb.dto.UsuarioDTO
import com.es.apirest_mongodb.dto.UsuarioRegisterDTO
import com.es.apirest_mongodb.exceptions.UnauthorizedException
import com.es.apirest_mongodb.service.TokenService
import com.es.apirest_mongodb.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("*"))
@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var usuarioService: UsuarioService


    @PostMapping("/register")
    fun insert(
        httpRequest: HttpServletRequest,
        @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
    ) : ResponseEntity<UsuarioDTO>{

        val usuarioInsertadoDTO: UsuarioDTO = usuarioService.insertUser(usuarioRegisterDTO)

        return ResponseEntity(usuarioInsertadoDTO, HttpStatus.CREATED)

    }

    @PostMapping("/login")
    fun login(
        @RequestBody usuario: LoginUsuarioDTO
    ) : ResponseEntity<Any>? {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas")
        }

        // SI PASAMOS LA AUTENTICACIÓN, SIGNIFICA QUE ESTAMOS BIEN AUTENTICADOS
        // PASAMOS A GENERAR EL TOKEN
        val token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
}