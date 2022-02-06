CREATE TABLE PLANET (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(400),
  distance VARCHAR(400),
  mass VARCHAR(400),
  created timestamp
);

CREATE TABLE SATELLITE (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  planet_id BIGINT NULL,
  name VARCHAR(400),
  distance VARCHAR(400),
  mass VARCHAR(400),
  created timestamp
);

ALTER TABLE SATELLITE
    ADD CONSTRAINT satellite_planet_id
    FOREIGN KEY (planet_id) REFERENCES planet(id)