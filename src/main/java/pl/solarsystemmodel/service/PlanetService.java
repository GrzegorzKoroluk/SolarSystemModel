package pl.solarsystemmodel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.solarsystemmodel.model.SolarSystemModels.Planet;
import pl.solarsystemmodel.model.SolarSystemModels.Satellite;
import pl.solarsystemmodel.repository.PlanetRepository;
import pl.solarsystemmodel.repository.SatelliteRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private static final int PAGE_SIZE = 20;
    private final PlanetRepository planetRepository;
    private final SatelliteRepository satelliteRepository;

    public List<Planet> getAll() {
        return planetRepository.findAll();
    }

    public List<Planet> getPlanetsPagination(int page) {
        return planetRepository.findAllPlanets(PageRequest.of(page, PAGE_SIZE));
    }

    @Cacheable(cacheNames = "SinglePlanet", key = "#id")
    public Planet getSingleObject(long id) {
        return planetRepository.findById(id)
                .orElseThrow();
    }

    public List<Planet> getObjectByName(String name) {
        return planetRepository.findAllByName(name);
    }

    @Cacheable(cacheNames = "PlanetsWithSatellites")
    public List<Planet> getPlanetsPaginationAndSorting(int page, Sort.Direction sort) {
        List<Planet> allPlanets = planetRepository.findAllPlanets(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
        List<Long> ids = allPlanets.stream().map(Planet::getId).collect(Collectors.toList());
        List<Satellite> satellites = satelliteRepository.findAllByPlanetIdIn(ids);
        allPlanets.forEach(planet -> planet.setSatellite(extractSatellites(satellites, planet.getId())));
        return allPlanets;
    }

    private List<Satellite> extractSatellites(List<Satellite> satellites, long id) {
        return satellites.stream().filter(satellite -> satellite.getPlanetId() == id).collect(Collectors.toList());
    }

    public Planet addPlanet(Planet planet) {
        return planetRepository.save(planet);
    }

    @Transactional
    @CachePut(cacheNames = "SinglePost", key = "#result.id")
    public Planet editPlanet(Planet planet) {
        Planet planetEdited = planetRepository.findById(planet.getId()).orElseThrow();
        planetEdited.setName(planet.getName());
        planetEdited.setDistance(planet.getDistance());
        planetEdited.setMass(planet.getMass());
        return planetEdited;
    }

    @CacheEvict(cacheNames = "SinglePost")
    public void deletePlanet(long id) {
        planetRepository.deleteById(id);
    }

    @CacheEvict(cacheNames = "PlanetsWithSatellites")
    public void clearPlanetsWithSatellites() {
    }
}
