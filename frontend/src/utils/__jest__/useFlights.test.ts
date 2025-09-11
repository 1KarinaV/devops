import { renderHook, act } from "@testing-library/react";
import useFlights from "../useFlights";
import { Flight } from "../../types/flight";

describe("useFlights", () => {
    const mockFlights: Flight[] = [
        {
            id: "1",
            origin: "Moscow",
            destination: "Paris",
            departure: new Date("2025-01-01T10:00:00Z"),
            arrival: new Date("2025-01-01T14:00:00Z"),
        },
        {
            id: "2",
            origin: "Berlin",
            destination: "London",
            departure: new Date("2025-01-02T09:00:00Z"),
            arrival: new Date("2025-01-02T10:30:00Z"),
        },
    ];

    beforeEach(() => {
        jest.spyOn(global, "fetch");
        jest.spyOn(console, "error").mockImplementation(() => {});
    });

    afterEach(() => {
        jest.restoreAllMocks();
    });

    it("Загрузка рейса", async () => {
        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: true,
            json: async () => mockFlights,
        });

        const { result } = renderHook(() => useFlights());

        await act(async () => {});

        expect(global.fetch).toHaveBeenCalledWith("/flights");
        expect(result.current.flights).toEqual(mockFlights);
    });

    it("Вывод ошибки при неудачной загрузке рейсов", async () => {
        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: false,
        });

        renderHook(() => useFlights());

        await act(async () => {});

        expect(console.error).toHaveBeenCalledWith(
            "Ошибка при загрузке рейсов:",
            expect.any(Error)
        );
    });

    it("Добавляется новый рейс", async () => {
        const newFlight: Flight = {
            id: "3",
            origin: "Paris",
            destination: "Rome",
            departure: new Date("2025-01-03T07:00:00Z"),
            arrival: new Date("2025-01-03T09:00:00Z"),
        };

        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: true,
            json: async () => [],
        });

        const { result } = renderHook(() => useFlights());
        await act(async () => {});

        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: true,
            json: async () => newFlight,
        });

        await act(async () => {
            await result.current.addFlight(newFlight);
        });

        expect(result.current.flights).toEqual([newFlight]);
    });

    it("Вывод ошибки при неудачном добавлении рейса", async () => {
        const newFlight: Flight = {
            id: "3",
            origin: "Paris",
            destination: "Rome",
            departure: new Date("2025-01-03T07:00:00Z"),
            arrival: new Date("2025-01-03T09:00:00Z"),
        };

        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: true,
            json: async () => [],
        });
        const { result } = renderHook(() => useFlights());
        await act(async () => {});

        (global.fetch as jest.Mock).mockResolvedValueOnce({
            ok: false,
        });

        await act(async () => {
            await result.current.addFlight(newFlight);
        });

        expect(console.error).toHaveBeenCalledWith(
            "Ошибка при добавлении рейса:",
            expect.any(Error)
        );
        expect(result.current.flights).toEqual([]);
    });

    // it("Обновление существующего рейса", async () => {
    //     const updatedFlight: Flight = {
    //         ...mockFlights[0],
    //         destination: "Tokyo",
    //     };

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: true,
    //         json: async () => mockFlights,
    //     });
    //     const { result } = renderHook(() => useFlights());
    //     await act(async () => {});

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: true,
    //         json: async () => updatedFlight,
    //     });

    //     await act(async () => {
    //         await result.current.updateFlight(updatedFlight);
    //     });

    //     expect(result.current.flights.find(f => f.id === "1")).toEqual(updatedFlight);
    // });

    // it("Вывод ошибку при неудачном обновлении рейса", async () => {
    //     const updatedFlight: Flight = {
    //         ...mockFlights[0],
    //         destination: "Tokyo",
    //     };

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: true,
    //         json: async () => mockFlights,
    //     });
    //     const { result } = renderHook(() => useFlights());
    //     await act(async () => {});

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: false,
    //     });

    //     await act(async () => {
    //         await result.current.updateFlight(updatedFlight);
    //     });

    //     expect(console.error).toHaveBeenCalledWith(
    //         "Ошибка при обновлении рейса:",
    //         expect.any(Error)
    //     );
    //     expect(result.current.flights.find(f => f.id === "1")).toEqual(mockFlights[0]);
    // });

    // it("Удаляется рейс", async () => {
    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: true,
    //         json: async () => mockFlights,
    //     });
    //     const { result } = renderHook(() => useFlights());
    //     await act(async () => {});

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({ ok: true });

    //     await act(async () => {
    //         await result.current.deleteFlight("1");
    //     });

    //     expect(result.current.flights.find(f => f.id === "1")).toBeUndefined();
    // });

    // it("Вывод ошибки при неудачном удалении рейса", async () => {
    //     (global.fetch as jest.Mock).mockResolvedValueOnce({
    //         ok: true,
    //         json: async () => mockFlights,
    //     });
    //     const { result } = renderHook(() => useFlights());
    //     await act(async () => {});

    //     (global.fetch as jest.Mock).mockResolvedValueOnce({ ok: false });

    //     await act(async () => {
    //         await result.current.deleteFlight("1");
    //     });

    //     expect(console.error).toHaveBeenCalledWith(
    //         "Ошибка при удалении рейса:",
    //         expect.any(Error)
    //     );
    //     expect(result.current.flights.find(f => f.id === "1")).toEqual(mockFlights[0]);
    // });
});