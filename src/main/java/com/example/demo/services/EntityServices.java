package com.example.demo.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.entities.MyEntity;
import com.example.demo.repository.EntityInterface;

@Service
public class EntityServices {
	EntityInterface entInter;
	
	public EntityServices(EntityInterface entInter) {
		this.entInter = entInter;
	}
	
	public MyEntity saveUser(MyEntity user) {
		return entInter.save(user);
	}

	public MyEntity getUserById(int id) {
		return entInter.findById(id).get();
	}
	
	public Optional<Integer> firstValue(int id){
		return getUserById(id).getNumList().stream().findFirst();
	}
	
	public Optional<Integer> maxValue(int id){
		return getUserById(id).getNumList().stream().max(Comparator.naturalOrder());
	}
	
	public List<Integer> repeatedValues(int id){
		Set<Integer> set = getUserById(id).getNumList().stream()
				.filter(i -> Collections.frequency(getUserById(id).getNumList(),i) > 1)
				.collect(Collectors.toSet());
		
		return set.stream().collect(Collectors.toList());
	}
	
	public String deleteUserbyId(int id) {
		entInter.delete(getUserById(id));
		return "user deleted";
	}
	
	public MyEntity updateUser(MyEntity entity, int id) {
		MyEntity any = new MyEntity(id,entity.getName(),entity.getNumList());
		return entInter.save(any);
	}
	
	public List<MyEntity> getAllUsers(){
		return (List<MyEntity>) entInter.findAll();
	}
	
	public boolean checkEmptyList(MyEntity entity) {
		if(entity.getNumList() == null || entity.getNumList().size() == 0) {
			return false;
		}else {
			return true;
		}
	}

	public boolean existId(int id) {
		return entInter.existsById(id);
	}

}
