package itmo.devops.unit.controller;

import itmo.devops.config.RepositoryConfig;
import itmo.devops.controller.FlightController;
import itmo.devops.dto.DeleteFlightDto;
import itmo.devops.dto.FlightDto;
import itmo.devops.model.Flight;
import itmo.devops.repository.FlightRepository;
import itmo.devops.service.FlightService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static itmo.devops.utils.JsonDeserializer.objectToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @MockitoBean
    private FlightService flightService;

    @MockitoBean
    private RepositoryConfig repositoryConfig;

    @MockitoBean
    private FlightRepository flightRepository;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Create successfully")
    @Test
    public void createTest() throws Exception {
        FlightDto flightDto = Instancio.create(FlightDto.class);

        String requestBody = objectToJson(flightDto);

        mockMvc.perform(post("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        Mockito.verify(flightService, Mockito.times(1)).create(flightDto);
    }

    @DisplayName("Create with exception")
    @Test
    public void createWithExceptionTest() throws Exception {
        FlightDto flightDto = Instancio.create(FlightDto.class);

        String requestBody = objectToJson(flightDto);

        Mockito.doThrow(new RuntimeException()).when(flightService).create(flightDto);

        mockMvc.perform(post("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("Delete with exception")
    @Test
    public void deleteWithExceptionTest() throws Exception {
        DeleteFlightDto deleteFlightDto = Instancio.create(DeleteFlightDto.class);
        String requestBody = objectToJson(deleteFlightDto);

        Mockito.doThrow(new RuntimeException()).when(flightService).delete(deleteFlightDto.getId());

        mockMvc.perform(delete("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("Delete successfully")
    @Test
    public void deleteTest() throws Exception {
        DeleteFlightDto deleteFlightDto = Instancio.create(DeleteFlightDto.class);
        String requestBody = objectToJson(deleteFlightDto);

        mockMvc.perform(delete("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Mockito.verify(flightService, Mockito.times(1)).delete(deleteFlightDto.getId());
    }

    @DisplayName("Get all with exception")
    @Test
    public void getAllWithExceptionTest() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(flightService).findAll();

        mockMvc.perform(get("/flights").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("Get all successfully")
    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get("/flights").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Mockito.verify(flightService, Mockito.times(1)).findAll();
    }

    @DisplayName("update with exception")
    @Test
    public void updateWithExceptionTest() throws Exception {
        Flight flight = Instancio.create(Flight.class);
        String requestBody = objectToJson(flight);

        Mockito.doThrow(new RuntimeException()).when(flightService).updateById(flight);

        mockMvc.perform(put("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("update successfully")
    @Test
    public void updateTest() throws Exception {
        Flight flight = Instancio.create(Flight.class);
        String requestBody = objectToJson(flight);

        Mockito.doReturn(flight).when(flightService).updateById(flight);
        mockMvc.perform(put("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Mockito.verify(flightService, Mockito.times(1)).updateById(flight);
    }

    @DisplayName("update nullable flight")
    @Test
    public void updateNullFlightTest() throws Exception {
        Flight flight = Instancio.create(Flight.class);
        String requestBody = objectToJson(flight);

        Mockito.doReturn(null).when(flightService).updateById(flight);
        Mockito.verify(flightService, Mockito.times(0)).updateById(flight);

        mockMvc.perform(put("/flight").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }
}
