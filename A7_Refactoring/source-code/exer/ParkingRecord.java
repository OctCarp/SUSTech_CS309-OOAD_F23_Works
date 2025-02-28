package exer;

import java.time.temporal.ChronoUnit;

public class ParkingRecord {
    private final Car car;

    private double parkingHours;

    public ParkingRecord(TimeRange timeRange, Car car) {
        this.car = car;
        this.parkingHours = ChronoUnit.MINUTES.between(timeRange.arriveTime(), timeRange.departureTime()) / 60.0;
    }

    public Car getCar() {
        return car;
    }

    public double getParkingHours() {
        return parkingHours;
    }


    @Override
    public String toString() {
        return String.format("%s -> parking %.1f hours", this.car.getCarNumber(), this.parkingHours);
    }
}
