package BikeApplication.Bike.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bike {

    @Id
    private String bikeId;
    private String ownName;
    private String mobile;
    private String bikeNo;
    private String brand;
    private String model;
    private Date bookAt;
    private String dateOfAppointment;
    private String description;
    private boolean inProcess;
    private boolean success;
    private String status;
    private boolean isHandOver;
}
