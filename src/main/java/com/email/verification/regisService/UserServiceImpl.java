package com.email.verification.regisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.email.verification.logic.EmailVerificationLogic;
import com.email.verification.registerEntity.RegisterUser;
import com.email.verification.registerEntity.User;
import com.email.verification.registrationDAO.UserDao;

import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

// Here we mention manually role of the user.
@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;	
	
	@Autowired
	private EmailVerificationLogic theEmailVerificationLogic; 
	// inject BCryptPasswordEndoer
//	@Autowired(required=true)
//	private PasswordEncoder passwordEncoder;

	@Override

	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public void save(RegisterUser registerUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(registerUser.getUserName());
		//user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
		user.setPassword(registerUser.getPassword());
		user.setFirstName(registerUser.getFirst_name());
		user.setLastName(registerUser.getLast_name());
		user.setEmail(registerUser.getEmail());
		user.setMobile(registerUser.getMobile());
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnable(false);
		// provide the filed in the React form and collect the roll like 
		// ROLE_ADMIN
		// ROLE_EMPLOYEE
		// ROLE_USER etc
		
		user.setRoles("ROLE_USER");	

		 // save user in the database
		List<String> emailVerifiacationFiledList = new LinkedList<String>();
		emailVerifiacationFiledList = userDao.save(user);
		if(emailVerifiacationFiledList != null)
		{			
			System.out.println("called");
			try {
				theEmailVerificationLogic.sendVerificationEmail(emailVerifiacationFiledList);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	return "completed";
		}
		else 
		{
			System.out.println("failed");
			//return "fail";
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;		
		User user = userDao.findByUserName(userName);
		if (user != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRoles()));
			// its show's that it is not define USER class but it's Spring defined user class we used
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + userName);	
	}
	
	// Email verification
	public boolean verify(String verificationCode) {
		System.out.println("sdfsfa"+ verificationCode);
		User user = userDao.verify(verificationCode);
		if (user == null || user.isEnable()) {
			return false;
		} else {
			System.out.println("verify 1");
			user.setVerificationCode(null);
			user.setEnable(true);
			userDao.Update(user);			
			return true;
		}
		
	}
}
