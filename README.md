
# Nombre del proyecto: Gestión Tareas del Hogar

# Descripción de los documentos y sus campos
## Documento Usuario
Representa a un usuario de la Aplicaión:
- id
- username
- email
- password
- role
- direccion

  ### SubDocumento Direccion
  Representa la Direccion de un Usuario:
  - calle
  - num
  - municipio
  - provincia
  - cp
  

## Documento Tarea
Representa una tarea en la Aplicación:
- id
- titulo
- descripcion
- estado
- usuario_id

# Endpoints

## Autenticación

- POST usuarios/register
- POST usuarios/login

## Tareas
- GET /tareas 
- GET /tareas/{id} 
- POST /tareas 
- PUT /tareas/{id} 
- DELETE /tareas/{id} 
- DELETE /tareas 


# Decripción Endpoints
### Autenticación
Estos méotdos podrán ser accedidos por todos, sin excepcion de roles.
- POST usuarios/register -> Registra un usuario
- POST usuarios/login -> Inicia sesión devolviendo el token de autenticación

### Tareas

- GET /tareas -> Obtiene todas las tareas del usuario
- GET /tareas/{id} -> Obtiene la tarea del usuario con esa id de tarea
- POST /tareas -> Inserta una tarea
- PUT /tareas/{id} -> Edita la tarea por id
- DELETE /tareas/{id} -> Elimina una tarea por su id
- DELETE /tareas -> Elimina todas las tareas

# Lógica de negocio

Cualquier usuario va a tener acceso tanto al login como al register.
En cuanto al rol USER, unicamente va a poder ver, editar, marcar como hecha, eliminar o añadir una tarea si es suya propia.
El rol ADMIN, podra hacer todo lo anterior, pero de cualquier otro usuario, no solo del suyo propio, exceptuando editar una tarea de otro usuario.
También podra ver todas las tareas en general o todas las tareas de un usuario en concreto.



# Excepciones y códigos de estado



|        Excepción       | Codigo estado HTTP |                                                                        Descripción                                                                        |                                                   Mensaje                                                  |
|:----------------------:|:------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
|   BadRequestException  |         400        | Se lanza cuando la solicitud realizada por el cliente es incorrecta o no válida, como cuando faltan parámetros obligatorios en el cuerpo de la solicitud. |                       Bad request exception (400). + El mensaje concreto de cada caso                      |
|    NotFoundException   |         404        |                                                   Se lanza cuando no se encuentra un recurso solicitado                                                   |                        Not Found Exception (404).+ El mensaje concreto de cada caso                        |
|    ConflictException   |         409        |                         Se lanza cuando se intenta crear o modificar un recurso que entra en conflicto con un recurso ya existente                        |                        Conflict Exception (409). + El mensaje concreto de cada caso                        |
| AlreadyExistsException |         409        |                         Se lanza cuando se intenta crear o modificar un recurso que entra en conflicto con un recurso ya existente                        |                        Conflict Exception (409). + El mensaje concreto de cada caso                        |
|  UnauthorizedException |         401        |                    Se lanza cuando el usuario no está autenticado o no ha proporcionado credenciales válidas para acceder a un recurso.                   |                      Unauthorized Exception (401). + El mensaje concreto de cada caso                      |
|   ForbiddenException   |         403        |                      Se lanza cuando el usuario no tiene permisos suficientes para acceder a un recurso, incluso si está autenticado.                     |                        Forbidden Exception (403). + El mensaje concreto de cada caso                       |
|   InternalServerError  |         500        |     Se lanza para capturar cualquier otro tipo de error no controlado por las excepciones anteriores. Esto puede ser un error de servidor inesperado.     | InternalServerError (500). An unexpected error ocurred (o algun otro personalizado en algun caso concreto) |

# Restricciones de seguridad

Se aplicará las siguientes restricciones:
- Autenticación con JWT para la identificación de usuarios.
- Hashing de contraseñas mediante bcrypt.
- Roles y permisos para restringir acciones.
- Validación de datos para evitar inyecciones y errores.
