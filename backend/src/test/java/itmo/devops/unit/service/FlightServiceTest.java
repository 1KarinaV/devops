package itmo.devops.unit.service;

import itmo.devops.config.RepositoryConfig;
import itmo.devops.dto.FlightDto;
import itmo.devops.model.Flight;
import itmo.devops.repository.FlightRepository;
import itmo.devops.service.FlightService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static itmo.devops.mapper.FLightMapper.toEntity;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebServiceClientTest(FlightService.class)
public class FlightServiceTest {

    @Autowired
    private FlightService flightService;

    @MockitoBean
    private RepositoryConfig repositoryConfig;

    @MockitoBean
    private FlightRepository flightRepository;

    @DisplayName("create successfully")
    @Test
    public void createTest() {
        FlightDto flightDto = Instancio.create(FlightDto.class);
        Flight flight = toEntity(flightDto);

        flightService.create(flightDto);

        Mockito.verify(flightRepository, Mockito.times(1)).save(flight);
    }

    @DisplayName("create with exception")
    @Test
    public void createWithExceptionTest() {
        FlightDto flightDto = Instancio.create(FlightDto.class);
        Flight flight = toEntity(flightDto);

        Mockito.doThrow(new IllegalArgumentException()).when(flightRepository).save(flight);

        assertThrows(IllegalArgumentException.class, () -> flightService.create(flightDto),
                "Исключение не соответствует ожидаемому");
        Mockito.verify(flightRepository, Mockito.times(1)).save(flight);
    }

    @DisplayName("delete successfully")
    @Test
    public void deleteTest() {
        Long id = Instancio.create(Long.class);
        flightService.delete(id);
        Mockito.verify(flightRepository, Mockito.times(1)).deleteById(id);
    }

    @DisplayName("update flight successfully")
    @Test
    public void getAllTest() {
        Flight flight = Instancio.create(Flight.class);
        flightService.updateById(flight);
        Mockito.verify(flightRepository, Mockito.times(1)).updateById(flight);
    }

    @DisplayName("update flight with exception")
    @Test
    public void getAllWithExceptionTest() {
        Flight flight = Instancio.create(Flight.class);

        Mockito.doThrow(new IllegalArgumentException()).when(flightRepository).updateById(flight);

        assertThrows(IllegalArgumentException.class, () -> flightService.updateById(flight),
                "Исключение не соответствует ожидаемому");
        Mockito.verify(flightRepository, Mockito.times(1)).updateById(flight);
    }

    @DisplayName("get all flight successfully")
    @Test
    public void updateTest() {
        flightService.findAll();
        Mockito.verify(flightRepository, Mockito.times(1)).findAll();
    }

    @DisplayName("get all flight with exception")
    @Test
    public void updateWithExceptionTest() {
        Mockito.doThrow(new IllegalArgumentException()).when(flightRepository).findAll();

        assertThrows(IllegalArgumentException.class, () -> flightService.findAll(),
                "Исключение не соответствует ожидаемому");
        Mockito.verify(flightRepository, Mockito.times(1)).findAll();
    }
}
