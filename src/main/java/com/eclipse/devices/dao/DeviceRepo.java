package com.eclipse.devices.dao;
 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.eclipse.devices.model.Device;
import com.eclipse.devices.model.User;

@Repository
public interface DeviceRepo  extends JpaRepository<Device, Long>{

	 

	Device getById(Integer id);
 

	boolean existsBySerial(Long serial);
	List<Device> findAllByUser(User user);


	Optional<List<Device>> findBySerialContaining(Long serial);
 
 

}
