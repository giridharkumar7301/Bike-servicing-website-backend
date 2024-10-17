package BikeApplication.Bike.Repositories;

import BikeApplication.Bike.entities.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BikeRepositories extends JpaRepository<Bike, String> {

    List<Bike> findByBikeNo(String bikeNo);
    //List<Bike> findByHandoverDay(String date);
    List<Bike> findByOwnName(String name);
    List<Bike> findBySuccessTrue();
    List<Bike> findByIsHandOverTrue();
    List<Bike> findByIsHandOverFalse();
    List<Bike> findByInProcessTrue();


}
