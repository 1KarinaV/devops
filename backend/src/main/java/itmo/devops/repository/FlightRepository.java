package itmo.devops.repository;

import itmo.devops.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Transactional
    @Query(value = """
            UPDATE flight
            set origin = :#{#flight.origin},
            destination = :#{#flight.destination},
            arrival = cast(:#{#flight.arrival} as TIMESTAMPTZ),
            departure = cast(:#{#flight.departure} as TIMESTAMPTZ)
            where id = :#{#flight.id}
            returning *
            """, nativeQuery = true)
    Flight updateById(@Param("flight") Flight flight);
}
