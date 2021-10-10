package com.eclipse.devices.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eclipse.devices.dao.RoleRepository;
import com.eclipse.devices.dao.UserRepo;
import com.eclipse.devices.exceptions.NotFoundException;
import com.eclipse.devices.model.Role;
import com.eclipse.devices.model.RoleName;
import com.eclipse.devices.model.User;

import io.swagger.annotations.ApiOperation;



@Transactional
@RestController
@RequestMapping(value = "api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserRepo userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired 
    RoleRepository roles;
    
    @ApiOperation("get user with specific id")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id ) throws Exception{
        return userRepository.findById(id).orElseThrow(()->new Exception("!!!!!"));
    }


    @ApiOperation("get all users")
    @GetMapping(value = "/all")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    
    @ApiOperation("get all users number")
    @GetMapping(value = "/count")
    public int count(){
        return userRepository.findAll().size();
    }
    
    @ApiOperation("delete user   ")
    @DeleteMapping(value="/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable Long id) throws NotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        userRepository.delete(user);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }

 
    @ApiOperation("update password")
    @PatchMapping("/pwdUpdate/{UID}")
    public Map<String,Boolean> updatePwd(@PathVariable Long UID, @RequestBody String pwd) throws NotFoundException {
    	User u = userRepository.findById(UID).orElseThrow(()->new NotFoundException("User not found"));

       u.setPassword(encoder.encode(pwd));
    	 userRepository.save(u);
    	   Map<String,Boolean> response = new HashMap<>();
           response.put("password updated "+u.getPassword(),Boolean.TRUE);
           return response;
    }

    @ApiOperation("find user with username like ")
    @GetMapping(value = "find/{username}")
    public List<User> getUser(@PathVariable String username ) throws Exception{
        return userRepository.findByUsernameContaining(username).orElseThrow(()->new Exception("!!!!!"));
    }
 
	@ApiOperation("is Admin ? ")
    @GetMapping(value = "isAdmin/{username}")
    public Set<Role> getRoles(@PathVariable String username) {
    Optional<User>	u= userRepository.findByUsername(username);
    return u.get().getRoles()  ;
    }

}
