package pl.solarsystemmodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.solarsystemmodel.model.SolarSystemModels.Satellite;

import java.util.List;

@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Long> {

    List<Satellite> findAllByPlanetIdIn(List<Long> ids);
}
