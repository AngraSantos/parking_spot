package com.parking_spot.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.parking_spot.model.ParkingSpotModel;
import com.parking_spot.repository.ParkingSpotRepository;


//A camada de serviço abrange as implementação da regras de ações da aplicação. 
@Service
public class ParkingSpotService {
	
	final ParkingSpotRepository parkingSpotRepository;
	
	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		this.parkingSpotRepository = parkingSpotRepository;
	}
	
	@Transactional //Irá fazer a transação do valor recebido da aplicação para o BD
	public ParkingSpotModel save (ParkingSpotModel parkingSpotModel) {
		return parkingSpotRepository.save(parkingSpotModel);
	}
	
	public List<ParkingSpotModel> findAll(){
		return parkingSpotRepository.findAll();
	}
	
	public Optional<ParkingSpotModel> findByID(UUID id){
		return parkingSpotRepository.findById(id);
	}
	
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);
	}

	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
	}
	
	
	
}
