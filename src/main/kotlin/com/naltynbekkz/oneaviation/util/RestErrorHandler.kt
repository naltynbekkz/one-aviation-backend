package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.util.entity.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class RestErrorHandler {
    @ExceptionHandler(ResponseStatusException::class)
    @ResponseBody
    fun processValidationError(
        e: ResponseStatusException,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Any {
        request.pathInfo
        response.status = e.status.value()
        return ErrorResponse(
            System.currentTimeMillis(),
            e.status.value(),
            e.status.reasonPhrase,
            e.reason,
            request.servletPath
        )
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
    @ResponseBody
    fun processSqlException(
        e: SQLIntegrityConstraintViolationException,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Any {
        response.status = HttpStatus.BAD_REQUEST.value()
        return ErrorResponse(
            System.currentTimeMillis(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.name,
            e.message,
            request.servletPath
        )
    }

    @ExceptionHandler(NullPointerException::class)
    @ResponseBody
    fun processSqlException(
        e: NullPointerException,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Any {
        response.status = HttpStatus.BAD_REQUEST.value()
        return ErrorResponse(
            System.currentTimeMillis(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.name,
            e.message,
            request.servletPath
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseBody
    fun noSuchElementException(
        e: NoSuchElementException,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Any {
        response.status = HttpStatus.BAD_REQUEST.value()
        return ErrorResponse(
            System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.name,
            e.message,
            request.servletPath
        )
    }

}