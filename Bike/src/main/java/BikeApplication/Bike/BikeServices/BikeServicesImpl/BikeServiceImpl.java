package BikeApplication.Bike.BikeServices.BikeServicesImpl;


import BikeApplication.Bike.BikeServices.BikeService;
import BikeApplication.Bike.Repositories.BikeRepositories;
import BikeApplication.Bike.entities.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepositories bikeRepositories;
    @Override
    public Bike bookBike(Bike bike) {
        return bikeRepositories.save(bike);
    }

    @Override
    public Bike update(String bikeId, Bike bike) {
        Bike bike1 = bikeRepositories.findById(bikeId).orElseThrow(
                ()-> new RuntimeException("Bike not found for given Id !!"));
        Bike updatedBike =Bike.builder()
                .bikeId(bikeId)
                .ownName(bike.getOwnName())
                .mobile(bike.getMobile())
                .bikeNo(bike.getBikeNo())
                .brand(bike.getBrand())
                .model(bike.getModel())
                .bookAt(bike1.getBookAt())
                .dateOfAppointment(bike.getDateOfAppointment())
                .description(bike.getDescription())
                .inProcess(bike.isInProcess())
                .success(bike.isSuccess())
                .status(bike.getStatus())
                .isHandOver(bike.isHandOver()).build();
        return bikeRepositories.save(updatedBike);
    }

    @Override
    public List<Bike> getAll() {
        List<Bike> bikes = bikeRepositories.findAll();
        return bikes;

    }

    @Override
    public List<Bike> getByNo(String bikeNo) {
        List<Bike> bikes = bikeRepositories.findByBikeNo(bikeNo);
        return bikes;
    }

    @Override
    public List<Bike> getByCurrentDate() {
        LocalDateTime date = LocalDateTime.now();

        List<Bike> bikes = bikeRepositories.findAll();
        List<Bike> bikes1 = new ArrayList<>();

        for(Bike bike : bikes) {
            String dayOfMonth=date.getDayOfMonth()>9 ? date.getDayOfMonth()+"" : "0"+(date.getDayOfMonth()+"");
            String monthOfYear=date.getMonthValue()>9 ? date.getMonthValue()+"": "0"+date.getMonthValue();
            String year=date.getYear()>9 ? date.getYear()+"" : "0"+date.getYear();
            String currDate=year+"-"+monthOfYear+"-"+dayOfMonth;
            if(currDate.equalsIgnoreCase(bike.getDateOfAppointment())
                    && !(bike.isInProcess() || bike.isSuccess() || bike.isHandOver())) bikes1.add(bike);

        }
        return bikes1;
    }

    @Override
    public List<Bike> getByOwnName(String ownName) {
        List<Bike> bikes = bikeRepositories.findByOwnName(ownName);
        return bikes;
    }

    @Override
    public List<Bike> getByInProcess() {
        List<Bike> bikes = bikeRepositories.findByInProcessTrue();
        return bikes;
    }

    @Override
    public List<Bike> getSuccess() {
        List<Bike> bikes = bikeRepositories.findBySuccessTrue();
        return bikes;
    }

    @Override
    public List<Bike> getNotHandOver() {
       List<Bike> bikes = bikeRepositories.findByIsHandOverFalse();
       return bikes;
    }

    @Override
    public List<Bike> getHandOverBike() {
        List<Bike> bikes = bikeRepositories.findByIsHandOverTrue();
        return bikes;
    }

    @Override
    public String getStatus(String bikeNo) {
        List<Bike> bikes = bikeRepositories.findAll();
        for(Bike bike : bikes){
            if(bike.getBikeNo().equalsIgnoreCase(bikeNo)){
                if(bike.isHandOver()==true) return "bike is delivered !! thank you for visiting !!";
                else if(bike.isSuccess()==true) return "bikes servicing completed !! thank you for visiting !!";
                else if(bike.isInProcess()) return "bike is in process";
                else return "give us 3 working days";
            }
        }
        return "bike is not found for given bike Number please enter details again !!";
    }

    @Override
    public void updateStatus(String statusType, String bikeNo) {
        List<Bike> bikes = bikeRepositories.findAll();

        for(Bike bike : bikes){
            if(bike.getBikeNo().equalsIgnoreCase(bikeNo)){
                if(statusType.equalsIgnoreCase("process")) bike.setInProcess(true);
                    else if(statusType.equalsIgnoreCase("success")) bike.setSuccess(true);
                        else if(statusType.equalsIgnoreCase("handover")) bike.setHandOver(true);
                            else if(statusType.equalsIgnoreCase("!process")) bike.setInProcess(false);
                                else if(statusType.equalsIgnoreCase("!success")) bike.setSuccess(false);
                                    else if(statusType.equalsIgnoreCase("!handover")) bike.setHandOver(false);
                                        bikeRepositories.save(bike);
                                            break;
            }
        }
    }

    @Override
    public void update(String statusType, String bikeId) {
        Bike bike = bikeRepositories.findById(bikeId)
                .orElseThrow(()->new RuntimeException("bike not found for given bikeId !!"));


            if(statusType.equalsIgnoreCase("process")) bike.setInProcess(true);
            else if(statusType.equalsIgnoreCase("success")) {
                bike.setSuccess(true);
                bike.setInProcess(false);
            }
            else if(statusType.equalsIgnoreCase("handover")) {
                bike.setHandOver(true);
                bike.setSuccess(false);
            }
            else if(statusType.equalsIgnoreCase("!process")) bike.setInProcess(false);
            else if(statusType.equalsIgnoreCase("!success")) bike.setSuccess(false);
            else if(statusType.equalsIgnoreCase("!handover")) bike.setHandOver(false);
            bikeRepositories.save(bike);
    }

}
