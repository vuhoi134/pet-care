package com.poly.petcare.domain.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, "Data conflict", new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
    logger.info(ex);
    return handleExceptionInternal(
        ex, "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN, request);
  }


  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<Object> ResourceNotFoundException(Exception ex, WebRequest webRequest) {
    log.info("======NotFoundException====: ");
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ValidDetails validDetails = new ValidDetails();
    Map<String, String> field = new HashMap<>();
    if (ex instanceof MethodArgumentNotValidException) {
      List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      for (FieldError fieldError : fieldErrors) {
        field.put(fieldError.getField(), fieldError.getDefaultMessage());
      }
      validDetails.setData(field);
    } else {
      field.put("default", ex.getLocalizedMessage());
      validDetails.setData(field);
    }

    Map.Entry<String, String> entry = field.entrySet().iterator().next();
    String message = entry.getValue();
    validDetails.setMessage(message);
    validDetails.setTimestamp(new Date());
    validDetails.setError("Not valid exception");
    validDetails.setPath(((ServletWebRequest) request).getRequest().getServletPath());

    // return exceptionMessageObj;
    return new ResponseEntity<>(validDetails, new HttpHeaders(), HttpStatus.OK);
  }
}