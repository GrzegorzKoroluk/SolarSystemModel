package pl.solarsystemmodel.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PlanetDto {
    private long id;
    private String name;
    private double distance;
    private double mass;
    private LocalDateTime created;
}
