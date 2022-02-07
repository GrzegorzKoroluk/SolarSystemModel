package pl.solarsystemmodel.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.solarsystemmodel.controller.dto.PlanetDto;
import pl.solarsystemmodel.controller.dto.PlanetDtoMapper;
import pl.solarsystemmodel.model.SolarSystemModels.Planet;
import pl.solarsystemmodel.service.PlanetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @GetMapping("/")
    public String mainPage() {
        return "<div>This is the main page of the SolarSystem creator. Go and check the following:</div>\n " +
                "<div>1. Go to <a href=\"http://localhost:8080/swagger-ui.html\">Swagger</a></div>\n " +
                "<div>2. Go to <a href=\"http://localhost:8080/h2-console\">H2-Console</a></div>\n";
    }

    @ApiOperation(value = "Shows all planets")
    @GetMapping(value = "/1.getAll")
    public List<PlanetDto> getAll() {
        return PlanetDtoMapper.mapToPlanetDtos(planetService.getAll());
    }


    @ApiOperation(value = "Shows all planets and adds pagination")
    @GetMapping(value = "/2.getAllPagination")
    public List<PlanetDto> getAllPagination(@RequestParam(required = false) Integer page,
                                            @AuthenticationPrincipal UsernamePasswordAuthenticationToken user /* do wee need this parameter here?*/) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        return PlanetDtoMapper.mapToPlanetDtos(planetService.getPlanetsPagination(pageNumber));
    }

    @ApiOperation(value = "Shows all planets and satellites and adds pagination and sorting")
    @GetMapping(value = "/3.getAllWithPaginationAndSorting")
    public List<Planet> getAllWithPaginationAndSorting(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort == null ? Sort.Direction.ASC : sort;
        return planetService.getPlanetsPaginationAndSorting(pageNumber, sortDirection);
    }

    @ApiOperation(value = "Shows single planet and it's satellites")
    @GetMapping(value = "/4.getSingleObjectById/{id}")
    public Planet getSingleObject(long id) {
        return planetService.getSingleObject(id);
    }


    @ApiOperation(value = "Shows single planet under a given name")
    @GetMapping(value = "/5.getSingleObjectByName/{name}")
    public List<Planet> getSingleObject(String name) {
        return planetService.getObjectByName(name);
    }


    @ApiOperation(value = "Adds a planet to solar system.")
    @PostMapping(value = "/6.planets")
    public Planet addPlanet(@RequestBody Planet planet) {
        return planetService.addPlanet(planet);
    }

    @ApiOperation(value = "Edits a planet in solar system.")
    @PutMapping(value = "/7.planets")
    public Planet editPlanet(@RequestBody Planet planet) {
        return planetService.editPlanet(planet);
    }

    @ApiOperation(value = "Deletes a planet in solar system.")
    @DeleteMapping(value = "/8.planets/{id}")
    public void deletePlanet(long id) {
        planetService.deletePlanet(id);
    }


}