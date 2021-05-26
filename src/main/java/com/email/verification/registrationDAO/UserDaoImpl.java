package com.email.verification.registrationDAO;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.email.verification.registerEntity.User;


@Repository
public class UserDaoImpl implements UserDao {

	//inject the session factory
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public User findByUserName(String theUserName) {
		//current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		//fetch from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", theUserName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	@Transactional
	public LinkedList<String> save(User theUser) {
		// current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		System.out.println("This is from userDAOImpl " +theUser.getMobile());
		
		LinkedList<String> emailVerificationDetail = new LinkedList<>();
		// Save to user data		
		if(currentSession.save(theUser) != null)
		{			
			emailVerificationDetail.add(theUser.getFirstName());
			emailVerificationDetail.add(theUser.getVerificationCode());
			System.out.println("true");
			return emailVerificationDetail;
		}
		else {
			System.out.println("false");
			return emailVerificationDetail;
		}
	}
		
	@Override
	@Transactional
	public User verify(String verificationCode) {
		//current hibernate session
				System.out.println("facalfalfga "+ verificationCode);
				Session currentSession = entityManager.unwrap(Session.class);

				//fetch from database using username
				Query<User> theQuery = currentSession.createQuery("from User where verification_code=:emailCode", User.class);
				theQuery.setParameter("emailCode", verificationCode);
				User theUser = null;
				try {
					theUser = theQuery.getSingleResult();
				} catch (Exception e) {
					theUser = null;
				}
				return theUser;
		
	}

	@Override
	@Transactional
	public void Update(User theUser) {
		// current hibernate session
		System.out.println(theUser.getVerificationCode());
				Session currentSession = entityManager.unwrap(Session.class);
				
				System.out.println("verify 2");
				
				currentSession.update(theUser);
	}

}
