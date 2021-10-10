package com.eclipse.devices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

 

@Entity
@Table(name="device") 
public class Device {
	 @Id
    @GeneratedValue
    private Long id;
   @Column(name="serial", nullable = false)
    private String serial; 

   @Column(name="status")
    private int status; 
   
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "owner_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonIgnore
   private User user;

public  Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getSerial() {
	return serial;
}

public void setSerial(String serial) {
	this.serial = serial;
}

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Device(String serial, int status) {
 
	this.serial = serial;
	this.status = status; 
}

public Device() {
	super();
}
    
    
    
   
   
    
}
   
   