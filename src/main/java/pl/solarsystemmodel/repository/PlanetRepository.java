package pl.solarsystemmodel.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.solarsystemmodel.model.SolarSystemModels.Planet;

import java.util.List;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    List<Planet> findAllByName(String name);

    @Query("Select p From Planet p left join fetch p.satellite")
    List<Planet> findAllPlanets(Pageable page);

    List<Planet> findAll();
}
