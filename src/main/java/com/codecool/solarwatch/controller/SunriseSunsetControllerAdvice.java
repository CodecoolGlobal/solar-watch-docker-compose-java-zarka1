package com.codecool.solarwatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SunriseSunsetControllerAdvice {
    @ResponseBody
    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String CityNotFoundExceptionHandler(CityNotFoundException ex)
    {
        return ex.getMessage();

    }

    @ResponseBody
    @ExceptionHandler(SunriseSunsetDateNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String SunriseSunsetDataNotAvailableExceptionHandler(
            SunriseSunsetDateNotAvailableException ex)
    {
        return ex.getMessage();

    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String UsernameNotFoundExceptionHandler(
            UsernameNotFoundException ex)
    {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String MissingServletRequestParameterHandler(
            MissingServletRequestParameterException ex)
    {
        return "Missing parameter: " + ex.getParameterName();
    }

    @ResponseBody
    @ExceptionHandler(SunriseSunsetDataExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String SunriseSunsetDataExistExeptionHandler(
            SunriseSunsetDataExistException ex)
    {
        return "Data already exist: " + ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CityExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String CityExistExeptionHandler(
            CityExistException ex)
    {
        return "Data already exist: " + ex.getMessage();
    }

}
