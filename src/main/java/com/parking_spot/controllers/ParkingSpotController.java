package com.parking_spot.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking_spot.dto.ParkingSpotDTO;
import com.parking_spot.model.ParkingSpotModel;
import com.parking_spot.services.ParkingSpotService;


//A camada de controle abrange as implementação da regras de negocio da aplicação. 
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController { 

	final ParkingSpotService parkingSpotService;
	
	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
		if(parkingSpotService.existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())) {			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Placa do carro já está em uso!");
		}
		if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Número da vaga já está em uso!");
		}
		if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Apartamento e bloco já estão em uso!");
		}
		
		ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
		parkingSpotModel.setResgistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}
	
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot() {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		 if(!parkingSpotModelOptional.isPresent()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot não encontrado!!!");
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}	
	
}
