package com.es.apirest_mongodb.controller

import com.es.apirest_mongodb.dto.CreateTareaDTO
import com.es.apirest_mongodb.dto.TareaDTO
import com.es.apirest_mongodb.exceptions.BadRequestException
import com.es.apirest_mongodb.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController{

    @Autowired
    private lateinit var tareaService: TareaService


    @PostMapping
    fun createTask(
        @RequestBody task: CreateTareaDTO,
        authentication: Authentication
    ): ResponseEntity<TareaDTO>{

        // Comprobar titulo y descripcion
        if (task.titulo.isBlank()) throw BadRequestException("El titulo de la tarea no puede ser vacío")
        if (task.descripcion.isBlank()) throw BadRequestException("La descripción de la tarea no puede ser vacío")

        val tarea = tareaService.createTask(task, authentication)

        return ResponseEntity(tarea, HttpStatus.CREATED)
    }


    /**
     * Obtener una tarea mediante su id
     */
    @GetMapping("/{tareaId}")
    fun getTaskById(
        @PathVariable tareaId: String,
        authentication: Authentication
    ): ResponseEntity<TareaDTO>{

        if (tareaId.isBlank()) throw BadRequestException("Debes introducirn una Id de tarea válida")

        val tarea = tareaService.getTaskById(tareaId, authentication)

        return ResponseEntity(tarea, HttpStatus.OK)
    }

    /**
     * Obtener todas las tareas del usuario
     */
    @GetMapping
    fun getAllTasks(
        authentication: Authentication
    ): ResponseEntity<List<TareaDTO>>{
        val tareas = tareaService.getAllTasks(authentication)
        return ResponseEntity(tareas, HttpStatus.OK)
    }


    @PutMapping("/{tareaId}")
    fun updateTaskById(
        @PathVariable tareaId: String,
        authentication: Authentication
    ): ResponseEntity<TareaDTO>{
        val tarea = tareaService.updateTask(tareaId,authentication)

        return ResponseEntity(tarea, HttpStatus.OK)
    }


    @DeleteMapping("/{tareaId}")
    fun deleteTaskById(
        @PathVariable tareaId: String,
        authentication: Authentication
    ){
        tareaService.deleteTaskById(tareaId, authentication)
    }

    @DeleteMapping
    fun deleteAllTasks(
        authentication: Authentication
    ){
        tareaService.deleteAllTasks(authentication)
    }


    // Editrar tarea (Marca como hecha)
    // Eliminar tarea


}