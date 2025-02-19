package com.es.apirest_mongodb.exceptions


class BadRequestException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Bad request exception (400)."
    }
}


class NotFoundException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Not Found Exception (404)."
    }
}


class ConflictException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Conflict Exception (409)."
    }
}

class AlreadyExistsException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Conflict Exception (409)."
    }
}

class UnauthorizedException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Unauthorized Exception (401)."
    }
}


class ForbiddenException(message: String) : RuntimeException("$DESCRIPTION $message"){
    companion object {
        const val DESCRIPTION = "Forbidden Exception (403)."
    }
}



/*

class UnauthorizedException(message: String) : RuntimeException("$DESCRIPTION  $message"){
    companion object {
        const val DESCRIPTION = "Unauthorized Exception (401)."
    }
}
 */





