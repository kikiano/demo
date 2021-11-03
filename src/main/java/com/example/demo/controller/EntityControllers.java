package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.entities.MyEntity;
import com.example.demo.services.EntityServices;

@RestController
@RequestMapping("/API")
public class EntityControllers {

	@Autowired
	EntityServices servicio;

	public EntityControllers(EntityServices servicio) {
		this.servicio = servicio;
	}

	@PostMapping(value = "/addUser")
	public MyEntity addNewUser(@RequestBody MyEntity entity) {
		return servicio.saveUser(entity);
	}

	@PutMapping(value = "/updateUser/{id}")
	public ResponseEntity<String> updateUser(@RequestBody MyEntity entity, @PathVariable(value = "id") Integer id) {
		// checar ID
		try {
			if (servicio.existId(id)) {
				servicio.updateUser(entity, id);
				return new ResponseEntity<>("the user: " + id + " has been updated: " + entity.getName() + " " + entity.getNumList(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("the user: " + id + " doesn´t exist", HttpStatus.BAD_REQUEST);
				}
		}catch(HttpMessageNotReadableException e) {
			return new ResponseEntity<>("check the numlist, must have only numbers",HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping(value = "/deleteUser/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") Integer id) {
		if (servicio.existId(id)) {
			return new ResponseEntity<>(servicio.deleteUserbyId(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("the user can´t be deleted because it doesn´t even exist! >:(",
					HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/printUser/{id}")
	public ResponseEntity<String> printUser(@PathVariable(value = "id") Integer id) {
		if (servicio.existId(id)) {
			return new ResponseEntity<>(printUserData(id),HttpStatus.OK);
		} else {
			return new ResponseEntity<>("the Id granted doesn´t exist",HttpStatus.OK);
		}

	}

	@GetMapping(value = "/printAll")
	public List<MyEntity> printAll() {
		return servicio.getAllUsers();
	}

	@GetMapping(value = "/maxNum/{id}")
	public ResponseEntity<String> maxNum(@PathVariable(value = "id") Integer id) {
		if (servicio.existId(id)) {
			if (servicio.checkEmptyList(id)) {
				return new ResponseEntity<>(printUserData(id) + "\n the Max num on the list is: " + servicio.maxValue(id),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("The user " + id + " has no numlist",HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("the Id you are looking for, doesn´t exist",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/firstNum/{id}")
	public ResponseEntity<String> firstNum(@PathVariable(value = "id") Integer id) {
		if (servicio.existId(id)) {
			if (servicio.checkEmptyList(id)) {
				return new ResponseEntity<>(printUserData(id) + "\n the first value of the list is: " + servicio.firstValue(id),HttpStatus.OK);
			} else {
				return new ResponseEntity<>("The user " + id + " has no numlist",HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("the Id you are looking for, doesn´t exist",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/repeatedNums/{id}")
	public ResponseEntity<String> repeatedNums(@PathVariable(value = "id") Integer id) {
		if (servicio.existId(id)) {
			if (servicio.checkEmptyList(id)) {
				if (servicio.repeatedValues(id).size() == 0) {
					return new ResponseEntity<>(printUserData(id) + "the numlist of this user has no repeated numbers",HttpStatus.OK);
				} else {
					return new ResponseEntity<>(printUserData(id) + "\n the repeated values on the list are: " + servicio.repeatedValues(id),HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>("The user " + id + " has no numlist",HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("the Id you are looking for, doesn´t exist",HttpStatus.NOT_FOUND);
		}

	}

	public String printUserData(Integer id) {
		MyEntity print = servicio.getUserById(id);
		return "User data: \n -ID: " 
				+ print.getId() + "\n -Name:" 
				+ print.getName() + "\n -NumList: "
				+ print.getNumList() + "\n";
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> HttpMessageNotReadableExceptionHandler(){
		return new ResponseEntity<>("check your entity inputs",HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> MethodArgumentTypeMismatchExceptionHandler(){
		return new ResponseEntity<>("must enter a valid id", HttpStatus.NOT_FOUND);
	}
	
}