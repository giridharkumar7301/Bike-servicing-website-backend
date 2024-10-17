package BikeApplication.Bike.BikeServices;

import BikeApplication.Bike.entities.Bike;

import java.util.List;

public interface BikeService {
    //book bike

    Bike bookBike(Bike bike);
    //updateBike
    Bike update(String bikeId , Bike bike);
    //getAllBike
    List<Bike> getAll();
    //getBikeByBikeNo
    List<Bike> getByNo(String bikeNo);
    //getByCurrentDate
    List<Bike> getByCurrentDate();
    //getBike by ownName
    List<Bike> getByOwnName(String ownName);
    //get bike in process
    List<Bike> getByInProcess();
    //get bike success
    List<Bike> getSuccess();
    List<Bike> getNotHandOver();
    List<Bike> getHandOverBike();

    //
    String getStatus(String bikeNo);

    void updateStatus(String statusType, String bikeNo);

    void update(String statusType , String bikeId);



}
