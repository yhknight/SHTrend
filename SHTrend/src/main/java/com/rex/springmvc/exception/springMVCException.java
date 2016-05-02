package com.rex.springmvc.exception;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@ControllerAdvice
public class springMVCException {
	
	@Autowired
	MessageSource messageSource;
	//private ObjectMapper om = new ObjectMapper();
	
	@ExceptionHandler
	public ExceptionMessage exceptionHandler(HttpServletRequest req,HttpServletResponse rep,Exception ex) throws IOException{
		
		ExceptionMessage em = new ExceptionMessage();
		em.setM_boday(messageSource.getMessage("999", new String[]{"--error!!"}, req.getLocale()));
		System.out.println(req.getLocale());
		em.setId(999);
		//rep.setCharacterEncoding("UTF-8");
		//om.writeValue(rep.getOutputStream(), em);
	
		return em;
	}

}
