package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jpalg
 * Created at 3/6/2022
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Resource not found.");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) {
        log.error("Not a number!");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
