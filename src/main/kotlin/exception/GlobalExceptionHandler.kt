// src/main/kotlin/org/example/exception/GlobalExceptionHandler.kt
package org.example.exception

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    data class ValidationError(
        val status: Int,
        val errors: Map<String, String?>
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ValidationError> {
        val fieldErrors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        val body = ValidationError(HttpStatus.BAD_REQUEST.value(), fieldErrors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict(@Suppress("UNUSED_PARAMETER") ex: DataIntegrityViolationException): ResponseEntity<Void> =
        ResponseEntity.status(HttpStatus.CONFLICT).build()
}
