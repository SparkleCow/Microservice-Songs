package cowradio.microservicesongs.controllers;

import cowradio.microservicesongs.exceptions.DuplicateAlbumException;
import cowradio.microservicesongs.exceptions.ErrorMessage;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler(value = NoResultException.class)
    public ResponseEntity<ErrorMessage> noResultExceptionHandler(NoResultException noResultException){
        ErrorMessage errorMessage = new ErrorMessage("Error 404 Not found", noResultException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        ErrorMessage errorMessage = new ErrorMessage("Error 400 Bad request", methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateAlbumException.class)
    public ResponseEntity<ErrorMessage> duplicateAlbumExceptionHandler(DuplicateAlbumException duplicateAlbumException) {
        ErrorMessage errorMessage = new ErrorMessage("Error 400 Bad request", duplicateAlbumException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}

