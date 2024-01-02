package ru.korotaev.weatherSensorRestApi.util;

public class DataNotCreatedException extends RuntimeException {
    public DataNotCreatedException(String msg) {
        super(msg);
    }
}