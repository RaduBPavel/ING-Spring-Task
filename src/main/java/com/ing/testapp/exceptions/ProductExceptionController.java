package com.ing.testapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = ProductIdNotFoundException.class)
    public ResponseEntity<Object> exception(ProductIdNotFoundException exception) {
        return new ResponseEntity<>("Product id not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidProductIdException.class)
    public ResponseEntity<Object> exception(InvalidProductIdException exception) {
        return new ResponseEntity<>("Invalid product id.", HttpStatus.NOT_ACCEPTABLE);
    }
}
