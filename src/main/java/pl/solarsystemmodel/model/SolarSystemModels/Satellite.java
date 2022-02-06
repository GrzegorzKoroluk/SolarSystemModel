package pl.solarsystemmodel.model.SolarSystemModels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long planetId;
    private String name;
    private double distance;
    private double mass;
    private LocalDateTime created;
}