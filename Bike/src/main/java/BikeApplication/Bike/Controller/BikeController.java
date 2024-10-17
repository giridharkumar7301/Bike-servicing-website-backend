package BikeApplication.Bike.Controller;


import BikeApplication.Bike.BikeServices.BikeService;
import BikeApplication.Bike.entities.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService;


    @CrossOrigin
    @PostMapping
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike){
        String bikeId= UUID.randomUUID().toString();
        bike.setBikeId(bikeId);
        bike.setBookAt(new Date());
        bike.setHandOver(false);
        bike.setInProcess(false);
        bike.setSuccess(false);
        bike.setStatus("wait upto "+bike.getDateOfAppointment());
        Bike saveBike = bikeService.bookBike(bike);
        return new ResponseEntity<>(saveBike , HttpStatus.CREATED);
    }

    //update
    @CrossOrigin
    @PutMapping("/{bikeId}")
    public ResponseEntity<Bike> update(@RequestBody Bike bike , @PathVariable String bikeId){
        Bike updatedBike = bikeService.update(bikeId , bike);
        return new ResponseEntity<>(updatedBike , HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Bike>> getAllBike(){
        List<Bike> bikes = bikeService.getAll();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/in_process")
    public ResponseEntity<List<Bike>> getBikesInProcess(){
        List<Bike> bikes = bikeService.getByInProcess();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }


   //List<Bike> findBySuccessTrue();
    @CrossOrigin
    @GetMapping("/success")
    public ResponseEntity<List<Bike>> getSuccess(){
        List<Bike> bikes = bikeService.getSuccess();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }

    //List<Bike> findByIsHandOverTrue();
    @CrossOrigin
    @GetMapping("/handover")
    public ResponseEntity<List<Bike>> getHandOvered(){
        List<Bike> bikes = bikeService.getHandOverBike();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }

   // List<Bike> findByIsHandOverFalse();
    @CrossOrigin
    @GetMapping("/!handover")
    public ResponseEntity<List<Bike>> handoverFalse(){
        List<Bike> bikes = bikeService.getNotHandOver();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/today")
    public ResponseEntity<List<Bike>> todayAppoint(){
        List<Bike> bikes = bikeService.getByCurrentDate();
        return new ResponseEntity<>(bikes , HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/status/{bikeNo}")
    public ResponseEntity<String> getBikeStatus(@PathVariable String bikeNo){
        String statusMassage = bikeService.getStatus(bikeNo);
        return new ResponseEntity<>(statusMassage , HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/update/{statusType}/{bikeId}")
    public ResponseEntity<HttpStatus> updateStatus(@PathVariable String statusType , @PathVariable String bikeId){
        bikeService.update(statusType , bikeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
