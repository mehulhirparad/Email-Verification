package com.email.verification.regisController;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.verification.regisService.UserService;
import com.email.verification.registerEntity.RegisterUser;
import com.email.verification.registerEntity.User;

@RestController
@RequestMapping("/api")
@ComponentScan("com.mgt.student")
public class RegistrationController 
{
	
	@Autowired
    private UserService userService;		
	
	// New user Registration API
		@RequestMapping(value="/email", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
		@ResponseBody
		public ResponseEntity<Map> saveUser(@Valid @RequestBody RegisterUser theRegisterUser,BindingResult result,
				HttpServletRequest request )
		{
			// getUserName from REGISTERUSER class for checking user is exist or not in the system			
			String userName = theRegisterUser.getUserName();
			System.out.println(""+userName);
			Map<String,String> errormap = new HashMap<String, String>();
			if(result.hasErrors()) {			
				for(FieldError error:result.getFieldErrors()) {
					errormap.put(error.getField(), error.getDefaultMessage());				
				}
				System.out.println(errormap);
				return new ResponseEntity<Map>(errormap,HttpStatus.OK);
			}
			
			// Search base on USERNAME is it exist or not 
			 User existing = userService.findByUserName(userName);
		        if (existing != null){
		        	errormap.put("message","User name already exists");
		            return new ResponseEntity<Map>(errormap,HttpStatus.OK);
		        }
		   	  userService.save(theRegisterUser);  
		   	  errormap.put("message",userName);
		   	  return new ResponseEntity<Map>(errormap,HttpStatus.OK);
			}		
		
	@RequestMapping(value="/registration1", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String studentRegistration1()
	{
		return "Hello1";
	}
}
