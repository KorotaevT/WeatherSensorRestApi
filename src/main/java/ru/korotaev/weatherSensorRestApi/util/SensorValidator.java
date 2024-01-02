package ru.korotaev.weatherSensorRestApi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.weatherSensorRestApi.models.Sensor;
import ru.korotaev.weatherSensorRestApi.services.SensorsService;

@Component
public class SensorValidator implements Validator {
    private  final SensorsService sensorsService;
    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor person = (Sensor) o;
        if(sensorsService.findOne(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}