package com.es.apirest_mongodb.controller

import com.es.apirest_mongodb.dto.CreateTareaDTO
import com.es.apirest_mongodb.dto.TareaDTO
import com.es.apirest_mongodb.exceptions.BadRequestException
import com.es.apirest_mongodb.service.AdminTareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminTareaController {

    @Autowired
    private lateinit var tareaService: AdminTareaService


    /**
     * Obtener todas las tareas que existen en el sistema
     */
    @GetMapping("/tareas")
    fun getAllTasks(): ResponseEntity<List<TareaDTO>> {
        val tareas = tareaService.getAllTasks()

        return ResponseEntity(tareas, HttpStatus.OK)
    }


    /**
     * Obtener todas las tareas de un usuario concreto
     */
    @GetMapping("/usuario/{usuarioId}/tareas")
    fun getAllUserTasks(
        @PathVariable usuarioId: String
    ): ResponseEntity<List<TareaDTO>>{
        val tareas = tareaService.getAllUsersTasks(usuarioId)

        return ResponseEntity(tareas, HttpStatus.OK)
    }

    /**
     * Crearle una nueva tarea a un usario concreto
     */
    @PostMapping("/usuario/{usuarioId}/tareas")
    fun createTaskForUser(
        @PathVariable usuarioId: String,
        @RequestBody createTareaDTO: CreateTareaDTO
    ): ResponseEntity<TareaDTO>{

        if (createTareaDTO.titulo.isBlank()) throw BadRequestException("El título no puede estar vacío")
        if (createTareaDTO.descripcion.isBlank()) throw BadRequestException("La descripción no puede estar vacía")

        val tarea = tareaService.createTaskForUser(usuarioId, createTareaDTO)

        return ResponseEntity(tarea, HttpStatus.CREATED)
    }

    @PutMapping("/tareas/{tareaId}/usuario/{usuarioId}")
    fun updateTaskFromUser(
        @PathVariable tareaId: String,
        @PathVariable usuarioId: String,
    ): ResponseEntity<TareaDTO>{
        val tarea = tareaService.updateTaskFromUser(usuarioId, tareaId)

        return ResponseEntity(tarea, HttpStatus.OK)
    }

    @DeleteMapping("/tareas/{tareaId}/usuario/{usuarioId}")
    fun deleteTaskFromUser(
        @PathVariable tareaId: String,
        @PathVariable usuarioId: String,
    ){
        tareaService.deleteTaskFromUser(usuarioId, tareaId)
    }


    @DeleteMapping("/usuario/{usuarioId}/tareas")
    fun deleteAllTasksFromUser(
        @PathVariable usuarioId: String,
    ){
        tareaService.deleteAllTasksFromUser(usuarioId)
    }




}