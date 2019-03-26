package com.seaso.seaso.common.exception;

import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "error";

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceConflictException.class)
    public JsonResponse<String> resourceConflict() {
        return new JsonResponse<>(HttpStatus.CONFLICT, "Resources conflict", "");
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public JsonResponse<String> resourceNotFound() {
        return new JsonResponse<>(HttpStatus.NOT_FOUND, "Resources not found", "");
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
