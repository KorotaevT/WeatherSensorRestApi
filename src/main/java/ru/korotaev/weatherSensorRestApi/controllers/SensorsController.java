package ru.korotaev.weatherSensorRestApi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.korotaev.weatherSensorRestApi.dto.SensorDTO;
import ru.korotaev.weatherSensorRestApi.models.Sensor;
import ru.korotaev.weatherSensorRestApi.services.SensorsService;
import ru.korotaev.weatherSensorRestApi.util.SensorErrorResponse;
import ru.korotaev.weatherSensorRestApi.util.SensorNotCreatedException;
import ru.korotaev.weatherSensorRestApi.util.SensorNotFoundException;
import ru.korotaev.weatherSensorRestApi.util.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;
    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registrations")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            FieldError error = bindingResult.getFieldErrors().get(0);
            stringBuilder.append(error.getField()).append(" - ")
                    .append(error.getDefaultMessage()).append(";");

            throw new SensorNotCreatedException(stringBuilder.toString());
        }
        sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> exception(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> exception(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}