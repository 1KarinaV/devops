import { useState, useEffect } from "react";
import { Flight } from "../types/flight";

const useFlights = () => {
    const [flights, setFlights] = useState<Flight[]>([]);

    useEffect(() => {
        const fetchFlights = async () => {
            try {
                const response = await fetch("http://backend-service:80/flights");
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                const data: Flight[] = await response.json();

                setFlights(data);
            } catch (error) {
                console.error("Ошибка при загрузке рейсов:", error);
            }
        };

        fetchFlights();
    }, []);

    const addFlight = async (flight: Flight) => {
        try {
            const response = await fetch("http://backend-service:80/flight", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(flight),
            });

            if (!response.ok) {
                throw new Error("Ошибка при добавлении рейса");
            }

            const newFlight: Flight = await response.json();
            setFlights((prev) => [...prev, newFlight]);
        } catch (error) {
            console.error("Ошибка при добавлении рейса:", error);
        }
    };

    const updateFlight = async (updatedFlight: Flight) => {
        try {
            const response = await fetch("http://backend-service:80/flight", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updatedFlight),
            });

            if (!response.ok) {
                throw new Error("Ошибка при обновлении рейса");
            }

            const updatedFlightData: Flight = await response.json();
            setFlights((prev) =>
                prev.map((flight) => (flight.id === updatedFlightData.id ? updatedFlightData : flight))
            );
        } catch (error) {
            console.error("Ошибка при обновлении рейса:", error);
        }
    };

    const deleteFlight = async (id: string) => {
        try {
            const response = await fetch("http://backend-service:80/flight", {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ id }),
            });

            if (!response.ok) {
                throw new Error("Ошибка при удалении рейса");
            }

            setFlights((prev) => prev.filter((flight) => flight.id !== id));
        } catch (error) {
            console.error("Ошибка при удалении рейса:", error);
        }
    };

    return { flights, addFlight, updateFlight, deleteFlight };
};

export default useFlights;