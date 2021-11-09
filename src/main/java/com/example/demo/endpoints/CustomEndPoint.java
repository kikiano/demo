package com.example.demo.endpoints;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom-endpoint")
public class CustomEndPoint {
	Map<String, Object> endpoint = new LinkedHashMap<String, Object>();
	
	@ReadOperation
	public Map<String,Object> aText() {
		endpoint.put("My details", "Hello World From My Endpoint :)");
		return endpoint;
	}
	
	@WriteOperation
	public void writtingSomethingNew(@Selector String val) {
		endpoint.put("My new thing",val);
		
	}
	
	@DeleteOperation
	public void deletingSomethin(@Selector String key) { 
		endpoint.remove(key);
	}
}