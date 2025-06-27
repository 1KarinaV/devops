package itmo.devops.service;

import itmo.devops.dto.FlightDto;
import itmo.devops.model.Flight;
import itmo.devops.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static itmo.devops.mapper.FLightMapper.toEntity;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository repository;

    @SneakyThrows
    public void create(FlightDto flightDto) {
        Flight flight = toEntity(flightDto);

        log.info(" Сохранить запись flight = {} в БД", flight);
        try {
            repository.save(flight);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Не удалось сохранить запись в БД");
        }
    }

    @SneakyThrows
    @Transactional
    public void delete(Long id) {
        log.info("Удалить запись по id = {}", id);
        repository.deleteById(id);
    }

    @SneakyThrows
    public List<Flight> findAll() {
        log.info("Вернуть все записи");
        return repository.findAll();
    }

    @SneakyThrows
    public Flight updateById(Flight flight) {
        log.info("Обновить запись на {}", flight);
        return repository.updateById(flight);
    }
}
