package pl.solarsystemmodel.controller.dto;

import pl.solarsystemmodel.model.SolarSystemModels.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetDtoMapper {

    private PlanetDtoMapper() {
    }

    public static List<PlanetDto> mapToPlanetDtos(List<Planet> planets) {
        return planets.stream().map(planet -> mapToPlanetDto(planet)).collect(Collectors.toList());
    }

    public static PlanetDto mapToPlanetDto(Planet planet) {
        return PlanetDto.builder()
                .id(planet.getId())
                .name(planet.getName())
                .distance(planet.getDistance())
                .mass(planet.getMass())
                .created(planet.getCreated())
                .build();

    }
}
