package konex.innovation.medicine_administration.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import konex.innovation.medicine_administration.dto.error.ErrorModelDto;
import konex.innovation.medicine_administration.dto.error.ErrorResponseDto;
import konex.innovation.medicine_administration.dto.web.ResponseDto;

@ControllerAdvice
public class ExceptionHandlerController {

    // Controlador de la excepcion cuando los datos no pasan correctamente las
    // validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> methodArgumentNotValidException(
            HttpServletRequest req,
            MethodArgumentNotValidException ex) {

        List<ErrorModelDto> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModelDto(err.getField(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(
                        false,
                        "Send a valid body",
                        ErrorResponseDto.builder().errors(errorMessages).build()));
    }

    // Controlador de la excepcion cuando no se envia un body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> httpMessageNotReadableException(
            HttpServletRequest req,
            HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(false, "Body not sent", null));

    }
}