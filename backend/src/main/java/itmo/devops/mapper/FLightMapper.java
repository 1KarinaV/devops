package itmo.devops.mapper;

import itmo.devops.dto.FlightDto;
import itmo.devops.model.Flight;

public class FLightMapper {

    public static Flight toEntity(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setOrigin(flightDto.getOrigin());
        flight.setDestination(flightDto.getDestination());
        flight.setArrival(flightDto.getArrival());
        flight.setDeparture(flightDto.getDeparture());

        return flight;
    }
}
