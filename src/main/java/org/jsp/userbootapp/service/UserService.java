package org.jsp.userbootapp.service;

import java.util.List;
import java.util.Optional;

import org.jsp.userbootapp.dao.UserDao;
import org.jsp.userbootapp.dto.ResponseStructure;
import org.jsp.userbootapp.dto.User;
import org.jsp.userbootapp.exception.IdNotFoundException;
import org.jsp.userbootapp.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class UserService {
@Autowired
 private UserDao dao;
  
public ResponseEntity<ResponseStructure<User>> saveUser( User u) {
	ResponseStructure<User> structure=new ResponseStructure<>();
	structure.setData(dao.saveUser(u));
	structure.setMessage("user saved with id:"+u.getId());
	structure.setStatuscode(HttpStatus.CREATED.value());
	return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.CREATED);
  }


public  ResponseEntity<ResponseStructure<User>> updateUser( User u) {
	ResponseStructure<User> structure=new ResponseStructure<>();
	structure.setData(dao.updateUser(u));
	structure.setMessage("user Updated");
	structure.setStatuscode(HttpStatus.ACCEPTED.value());
	return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.ACCEPTED);
	
}

public  ResponseEntity<ResponseStructure<User>> findById( int id) {
	ResponseStructure<User> structure=new ResponseStructure<>();
	Optional<User>recUser=dao.findById(id);
	if(recUser.isPresent())
	{
		structure.setData(recUser.get());
	structure.setMessage("user found");
	structure.setStatuscode(HttpStatus.OK.value());
	return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		
	}
	
		throw new IdNotFoundException();
		
	
}


public ResponseEntity<ResponseStructure<String>> deleteUser( int id) {
	ResponseStructure<String> structure=new ResponseStructure<>();
	Optional<User>recUser=dao.findById(id);
	if(recUser.isPresent()) {
		structure.setData("user found");
		structure.setMessage("user deleted");
		structure.setStatuscode(HttpStatus.OK.value());
		dao.deleteUser(id);
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
	}
	    
	structure.setData("user not found");
	structure.setMessage("user not deleted");
	structure.setStatuscode(HttpStatus.NOT_FOUND.value());
	return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);

	}
   public ResponseEntity<ResponseStructure<List<User>>> findAll(){
	   ResponseStructure<List<User>>structure=new ResponseStructure<>();
	   structure.setData(dao.findAll());
	   structure.setMessage("List of all Users");
	   structure.setStatuscode(HttpStatus.OK.value());
	   return new ResponseEntity<ResponseStructure<List<User>>>(structure,HttpStatus.OK);
	   
   }
  public ResponseEntity<ResponseStructure<User>> verifyUser(long phone,String password){
	  ResponseStructure<User>structure=new ResponseStructure<>();
	  Optional<User>recUser=dao.verifyUser(phone, password);
	  if(recUser.isPresent()) {
		  structure.setData(recUser.get());
		  structure.setMessage("User verifyed successfully");
		  structure.setStatuscode(HttpStatus.OK.value());
		  return new   ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.OK);
	  }
	 throw new InvalidCredentialsException();
   }
  
   public ResponseEntity<ResponseStructure<User>> verifyUser(String email,String password){
	 ResponseStructure<User>structure=new ResponseStructure<>();
	 Optional<User>recUser=dao.verifyUser(email, password);
	 if(recUser.isPresent()) {
		 structure.setData(recUser.get());
		 structure.setMessage("User verifyed successfully");
		 structure.setStatuscode(HttpStatus.OK.value());
		 return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.OK);
	 }
    throw new InvalidCredentialsException();
	  
  }
  
	

}
