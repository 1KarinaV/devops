import React, { useState } from "react";
import Button from "@mui/material/Button";
import useFlights from "./utils/useFlights";
import FlightTable from "./components/FlightTable";
import CreateModal from "./components/CreateModal";
import UpdateModal from "./components/UpdateModal";
import { Flight } from "./types/flight";


const App: React.FC = () => {
    const { flights, addFlight, updateFlight, deleteFlight } = useFlights();
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedFlight, setSelectedFlight] = useState<Flight | null>(null);

    const openCreateModal = () => {
        setSelectedFlight(null);
        setIsCreateModalOpen(true);
    };

    const openEditModal = (flight: Flight) => {
        setSelectedFlight(flight);
        setIsEditModalOpen(true);
    };

    const closeCreateModal = () => setIsCreateModalOpen(false);
    const closeEditModal = () => {
        setIsEditModalOpen(false);
        setSelectedFlight(null);
    };

    const handleCreateSave = async (flight: Flight) => {
        await addFlight(flight);
        closeCreateModal();
    };

    const handleUpdateSave = async (flight: Flight) => {
        await updateFlight(flight);
        closeEditModal();
    };

    return (
        <div style={{ padding: 20 }}>
            <h2>Онлайн Табло</h2>
            <Button variant="contained" color="primary" onClick={openCreateModal}>Добавить рейс</Button>
            <FlightTable flights={flights} onEdit={openEditModal} onDelete={deleteFlight} />
            <CreateModal isOpen={isCreateModalOpen} onClose={closeCreateModal} onSave={handleCreateSave} />
            <UpdateModal isOpen={isEditModalOpen} flight={selectedFlight} onClose={closeEditModal} onSave={handleUpdateSave} />
        </div>
    );
};

export default App;