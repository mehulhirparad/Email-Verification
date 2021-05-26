package com.email.verification.Controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.verification.regisService.UserService;

@RestController
@RequestMapping("/emailverification")
@ComponentScan("com.email.verification")
public class EmailVerificationConroller
{
	@Autowired
    private UserService theUserService;
	
	// Email verification link 
			@RequestMapping(value="/verify", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
			@ResponseBody		
			public String verifyUser(@Param("code") String code) 
			{			
				if (theUserService.verify(code)) {
					return "Thank you for varification ";
				} else {
					return "verify_fail";
				}
			}
}
