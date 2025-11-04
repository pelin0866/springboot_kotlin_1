package org.example.api

import java.time.Instant

data class ErrorResponse(
    val timestamp: Instant = Instant.now(),
    val status: Int, //400, 404, 409, 500
    val error: String, //Bad Request, Not Found
    val message:String?, //explanation
    val path:String, //users/123, URL for the error
    val errors: List<FieldErrorItem>? = null //field based errors
)

data class FieldErrorItem(
    val field: String, //email
    val message: String, //must be a well-formed email
    val rejectedValue: Any? = null //mistaken value
)
