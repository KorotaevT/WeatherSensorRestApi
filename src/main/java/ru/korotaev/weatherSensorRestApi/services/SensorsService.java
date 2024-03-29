package ru.korotaev.weatherSensorRestApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korotaev.weatherSensorRestApi.models.Sensor;
import ru.korotaev.weatherSensorRestApi.repositories.SensorsRepository;
import ru.korotaev.weatherSensorRestApi.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }
    public Sensor findOne(int id) {
        return sensorsRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }
    public Optional<Sensor> findOne(String name) {
        return sensorsRepository.findByName(name);
    }
    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}