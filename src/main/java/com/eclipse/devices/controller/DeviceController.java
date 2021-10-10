package com.eclipse.devices.controller;
 
import java.util.List; 
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eclipse.devices.dao.DeviceRepo;
import com.eclipse.devices.dao.UserRepo;
import com.eclipse.devices.exceptions.NotFoundException;
import com.eclipse.devices.model.Device;
import com.eclipse.devices.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "devices management")
@RestController
@RequestMapping(value="api/devices/")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {
    @Autowired
    DeviceRepo deviceRepository;
    
    @Autowired 
    UserRepo userRepository;

    @ApiOperation("get all devices")
    @GetMapping()
    public List<Device> all (){
        return deviceRepository.findAll();
    }

    @ApiOperation("get number of all devices")
    @GetMapping("count")
    public int Countall (){
        return deviceRepository.findAll().size();
    }
    @ApiOperation("get   device with specific id")
    @GetMapping(value="{id}")
    public ResponseEntity<Device> getById(@PathVariable Long id) throws Exception{
        final Device Device = deviceRepository.findById(id).orElseThrow(()->new Exception("not found"));
        return ResponseEntity.ok().body(Device);
    }
   
    @ApiOperation("get   device belongs to user with UID ")
    @GetMapping(value="user/{UID}")
    public ResponseEntity<List<Device>> belongsTo(@PathVariable Long UID) throws Exception{
    	final User user = userRepository.getById(UID);
        final List<Device> Devices = deviceRepository.findAllByUser(user);
        return ResponseEntity.ok().body(Devices);
    }
    
    @ApiOperation("get   device's owner ")
    @GetMapping(value="/Owner/{DID}")
    public ResponseEntity<Long> getOwner(@PathVariable Long DID) throws Exception{
    	final Long UID = deviceRepository.getById(DID).getUser().getId();
    	 
        return ResponseEntity.ok().body(UID);
    }
    
    @ApiOperation("find device with serial like ")
    @GetMapping(value = "find/{serial}")
    public List<Device> getUser(@PathVariable Long serial ) throws Exception{
        return deviceRepository.findBySerialContaining(serial).orElseThrow(()->new Exception("serial not found"));
    }
    
    @ApiOperation("get serial")
    @GetMapping(value="generator")
    public ResponseEntity<Long> generate(){
       Long serial;
	do {
    	Random rd = new Random();
    	 serial =   rd.nextLong();
    	 if(serial<0)
    		 serial *= -1;
       }
    	while(deviceRepository.existsBySerial(serial));
	 
    	return ResponseEntity.ok().body(serial);
    }
 

    @ApiOperation("update device status")
    @PutMapping("{DID}")
    public Device updateDevice( 
                                 @PathVariable (value = "DID") Long DID,
                                 @Valid @RequestBody Integer status ) 
                                		 throws NotFoundException {
      

        return deviceRepository.findById(DID).map(d -> {
            d.setStatus(status); 
            return deviceRepository.save(d);
        }).orElseThrow(() -> new  NotFoundException("device " + DID + "not found"));
    }
 

    @ApiOperation("delete device ")
    @DeleteMapping("{DID}")
    public void deleteDevice( 
                              @PathVariable (value = "DID") Long DID) throws NotFoundException {
          deviceRepository.deleteById(DID);
 
    }

   
    

    @ApiOperation("add new device to the user with UID")
    @PostMapping("{UID}")
    public Device addDevice(@PathVariable (value = "UID") Long id,
                                 @Valid @RequestBody Device device)
                                		 throws NotFoundException {
        return userRepository.findById(id).map(user -> {
            device.setUser(user);
            return deviceRepository.save(device);
        }).orElseThrow(() -> new NotFoundException("user " + id + " not found"));
    }
    
    
    

 
    
  
}
