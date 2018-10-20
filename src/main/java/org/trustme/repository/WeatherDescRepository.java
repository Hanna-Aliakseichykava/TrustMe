package org.trustme.repository;

import org.trustme.domain.WeatherDesc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WeatherDesc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeatherDescRepository extends JpaRepository<WeatherDesc, Long> {

}
