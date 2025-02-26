package com.es.apirest_mongodb.service

import com.es.apirest_mongodb.dto.CreateTareaDTO
import com.es.apirest_mongodb.dto.TareaDTO
import com.es.apirest_mongodb.exceptions.NotFoundException
import com.es.apirest_mongodb.exceptions.UnauthorizedException
import com.es.apirest_mongodb.model.Tarea
import com.es.apirest_mongodb.repository.TareaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun createTask(
        task: CreateTareaDTO,
        authentication: Authentication
    ): TareaDTO{
        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = Tarea(
            titulo = task.titulo,
            descripcion = task.descripcion,
            usuarioId = usuario.id!!
        )

        val tareaGuardada = tareaRepository.save(tarea)

        val tareaDTO = TareaDTO(
            id = tareaGuardada.id!!,
            titulo = tareaGuardada.titulo,
            descripcion = tareaGuardada.descripcion,
            estado = tareaGuardada.estado,
            usuarioId = usuario.id,
        )

        return tareaDTO
    }


    fun getTaskById(
        taskId: String,
        authentication: Authentication
    ): TareaDTO{

        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = tareaRepository.findById(taskId).orElseThrow { NotFoundException("Tarea con Id '$taskId' no encontrada") }

        // Comprobar que el dueño es el autenticado
        if (tarea.usuarioId != usuario.id) throw UnauthorizedException("No tienes permiso para ver esta tarea")

        val tareaDTO = TareaDTO(
            id = tarea.id!!,
            titulo = tarea.titulo,
            descripcion = tarea.descripcion,
            estado = tarea.estado,
            usuarioId = usuario.id
        )

        return tareaDTO
    }


    fun getAllTasks(
        authentication: Authentication
    ): List<TareaDTO> {
        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tasks = tareaRepository.findByUsuarioId(usuario.id!!).ifEmpty { throw NotFoundException("El usuario no tiene tareas en este momento") }.map { tarea ->
            TareaDTO(
                tarea.id!!,
                tarea.titulo,
                tarea.descripcion,
                tarea.estado,
                tarea.usuarioId
            )
        }

        return tasks
    }


    fun updateTask(
        taskId: String,
        authentication: Authentication,
    ): TareaDTO{
        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = tareaRepository.findById(taskId).orElseThrow { NotFoundException("Error - Tarea con id '$taskId' no encontrado") }

        if (usuario.id != tarea.usuarioId) throw UnauthorizedException("No tienes permiso para editar esta tarea")

        val tareaActualizada = tarea.copy(
            estado = if (tarea.estado == "PENDIENTE") "COMPLETADA" else "PENDIENTE"
        )

        tareaRepository.save(tareaActualizada)

        val tareaDTO = TareaDTO(
            id = tareaActualizada.id!!,
            titulo = tareaActualizada.titulo,
            descripcion = tareaActualizada.descripcion,
            estado = tareaActualizada.estado,
            usuarioId = tareaActualizada.usuarioId
        )

        return tareaDTO
    }


    fun deleteTaskById(
        taskId: String,
        authentication: Authentication
    ){
        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tarea = tareaRepository.findById(taskId).orElseThrow { NotFoundException("Error - Tarea con id '$taskId' no encontrado") }

        if (usuario.id != tarea.usuarioId) throw UnauthorizedException("No tienes permiso para eliminar esta tarea")

        tareaRepository.delete(tarea)
    }


    fun deleteAllTasks(
        authentication: Authentication
    ){
        val usuario = usuarioService.getUserByUsername(authentication.name).orElseThrow { NotFoundException("Error - Usuario no encontrado") }

        val tareas = tareaRepository.findByUsuarioId(usuario.id!!).ifEmpty { throw NotFoundException("Este usuario no tiene tareas para eliminar") }

        tareaRepository.deleteAll(tareas)
    }
}