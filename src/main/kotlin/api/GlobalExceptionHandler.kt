package org.example.api

import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 400 - @Valid body (DTO) alan hataları
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        req: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.bindingResult
            .allErrors
            .mapNotNull { err ->
                if (err is FieldError) {
                    FieldErrorItem(
                        field = err.field,
                        message = err.defaultMessage ?: "Validation error",
                        rejectedValue = err.rejectedValue
                    )
                } else null
            }

        return build(HttpStatus.BAD_REQUEST, "Validation failed", req, fieldErrors)
    }

    // 400 - @RequestParam / @PathVariable validation
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        ex: ConstraintViolationException,
        req: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val items = ex.constraintViolations.map { v ->
            FieldErrorItem(
                field = v.propertyPath.toString(),
                message = v.message,
                rejectedValue = v.invalidValue
            )
        }
        return build(HttpStatus.BAD_REQUEST, "Constraint violation", req, items)
    }

    // 400 - Parametre eksik / Body parse edilemedi
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParam(
        ex: MissingServletRequestParameterException,
        req: HttpServletRequest
    ) = build(HttpStatus.BAD_REQUEST, "Missing request parameter: ${ex.parameterName}", req)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnreadable(
        ex: HttpMessageNotReadableException,
        req: HttpServletRequest
    ) = build(HttpStatus.BAD_REQUEST, "Malformed JSON request", req)

    // 404 - Not Found
    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class
    )
    fun handleNotFound(ex: Exception, req: HttpServletRequest) =
        build(HttpStatus.NOT_FOUND, ex.message ?: "Not found", req)

    // 409 - Örn. unique email çakışması
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict(ex: DataIntegrityViolationException, req: HttpServletRequest) =
        build(HttpStatus.CONFLICT, "Data integrity violation", req)

    // 403 - Yetki yok (Security aktifse)
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException, req: HttpServletRequest) =
        build(HttpStatus.FORBIDDEN, ex.message ?: "Access denied", req)

    // 404 - delete(id) için kayıt yoksa
    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResult(ex: EmptyResultDataAccessException, req: HttpServletRequest) =
        build(HttpStatus.NOT_FOUND, "Resource not found", req)

    // 400 - Kötü istekler
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArg(ex: IllegalArgumentException, req: HttpServletRequest) =
        build(HttpStatus.BAD_REQUEST, ex.message ?: "Bad request", req)

    // 500 - Beklenmeyenler
    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception, req: HttpServletRequest) =
        build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", req)

    // ---- helper ----
    private fun build(
        status: HttpStatus,
        message: String,
        req: HttpServletRequest,
        fieldErrors: List<FieldErrorItem>? = null
    ): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            path = req.requestURI,
            errors = fieldErrors
        )
        return ResponseEntity.status(status).body(body)
    }
}
