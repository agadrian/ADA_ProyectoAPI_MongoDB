package com.es.apirest_mongodb.service

import com.es.apirest_mongodb.dto.CreateTareaDTO
import com.es.apirest_mongodb.dto.TareaDTO
import com.es.apirest_mongodb.exceptions.BadRequestException
import com.es.apirest_mongodb.exceptions.NotFoundException
import com.es.apirest_mongodb.model.Tarea
import com.es.apirest_mongodb.repository.TareaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminTareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun getAllTasks(): List<TareaDTO> {
        val tareas = tareaRepository.findAll().ifEmpty { throw NotFoundException("No hay ninguna tarea en el sistema") }.map { tarea ->
            TareaDTO(
                id = tarea.id!!,
                titulo = tarea.titulo,
                descripcion = tarea.descripcion,
                estado = tarea.estado,
                usuarioId = tarea.usuarioId,
            )
        }

        return tareas
    }


    fun getAllUsersTasks(
        usuarioId: String,
    ): List<TareaDTO> {
        val user = usuarioService.getUserById(usuarioId).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tareas = tareaRepository.findByUsuarioId(user.id!!).ifEmpty { throw NotFoundException("Este usuario no tiene tareas") }.map { tarea ->
            TareaDTO(
                id = tarea.id!!,
                titulo = tarea.titulo,
                descripcion = tarea.descripcion,
                estado = tarea.estado,
                usuarioId = user.id,
            )
        }

        return tareas
    }


    fun createTaskForUser(
        usuarioId: String,
        tarea: CreateTareaDTO

    ): TareaDTO{

        val user = usuarioService.getUserById(usuarioId).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val nuevaTarea = Tarea(
            titulo = tarea.titulo,
            descripcion = tarea.descripcion,
            usuarioId = user.id!!
        )

        val tareaGuardada = tareaRepository.save(nuevaTarea)

        val tareaDTO = TareaDTO(
            id = tareaGuardada.id!!,
            titulo = tareaGuardada.titulo,
            descripcion = tareaGuardada.descripcion,
            estado = tareaGuardada.estado,
            usuarioId = user.id
        )

        return tareaDTO
    }



    fun updateTaskFromUser(
        usuarioId: String,
        tareaId: String
    ): TareaDTO {
        val user = usuarioService.getUserById(usuarioId).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = tareaRepository.findById(tareaId).orElseThrow { NotFoundException("Error - Tarea con id '$tareaId' no encontrada") }

        if (user.id != tarea.usuarioId) throw BadRequestException("La tarea seleccionada no pertenece al usuario seleccionado")

        val tareaActualizada = tarea.copy(
            estado = if (tarea.estado == "PENDIENTE") "COMPLETADA" else "PENDIENTE"
        )

        tareaRepository.save(tareaActualizada)

        val tareaDTO = TareaDTO(
            id = tareaActualizada.id!!,
            titulo = tareaActualizada.titulo,
            descripcion = tareaActualizada.descripcion,
            estado = tareaActualizada.estado,
            usuarioId = user.id
        )

        return tareaDTO
    }



    fun deleteTaskFromUser(
        usuarioId: String,
        tareaId: String
    ){
        val user = usuarioService.getUserById(usuarioId).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = tareaRepository.findById(tareaId).orElseThrow { NotFoundException("Error - Tarea con id '$tareaId' no encontrada") }

        if (user.id != tarea.usuarioId) throw BadRequestException("La tarea seleccionada no pertenece al usuario seleccionado")

        tareaRepository.delete(tarea)
    }


    fun deleteAllTasksFromUser(
        usuarioId: String
    ){
        val user = usuarioService.getUserById(usuarioId).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tareas = tareaRepository.findByUsuarioId(user.id!!).ifEmpty { throw NotFoundException("Este usuario no tiene tareas") }

        tareaRepository.deleteAll(tareas)
    }
}