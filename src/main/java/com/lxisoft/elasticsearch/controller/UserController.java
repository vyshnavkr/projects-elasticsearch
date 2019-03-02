package com.lxisoft.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.elasticsearch.model.User;
import com.lxisoft.elasticsearch.service.UserService;
import com.lxisoft.elasticsearch.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    
    //GET ALL USERS FROM ELASTICSEARCH
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(
    		Pageable pageable, 
    		@RequestParam(required = false, defaultValue = "false") boolean eagerload) {	
    	
    	List<User> users = userService.getAllUsers();												//LIST
        Page<User> page = new PageImpl<User>(users);											//PAGE
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(						//HTTP HEADER
        						page, 
        						String.format("/api/elasticsearch/users?eagerload=%b", 
        								eagerload));
        
        return ResponseEntity.ok().headers(headers).body((page.getContent()));					//RETURN RESPONSE ENTITY
    }
    
    
    //SEARCH USERS BY ANY INPUT DATA, BE IT KEYWORD OR TEXT PHRASE
    @RequestMapping(value = "/users/search", method = RequestMethod.POST)
    public ResponseEntity<List<User>> searchUsersByText(
    		@RequestBody String searchTerm, 
    		Pageable pageable, 
    		@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        
    	List<User> users = userService.searchUsers(searchTerm);										//LIST
        Page<User> page = new PageImpl<User>(users);											//PAGE
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(						//HTTP HEADER
        		page, 
        		String.format("/api/elasticsearch/users/search?eagerload=%b", 
        				eagerload));
        	
        return ResponseEntity.ok().headers(headers).body((page.getContent()));					//RETURN RESPONSE ENTITY
    }

  
}