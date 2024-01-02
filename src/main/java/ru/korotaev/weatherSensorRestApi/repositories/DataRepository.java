package ru.korotaev.weatherSensorRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korotaev.weatherSensorRestApi.models.Data;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {
    List<Data> findAllByRaining(Boolean bool);
}
