package org.trustme.repository;

import org.trustme.domain.Visited;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Visited entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitedRepository extends JpaRepository<Visited, Long> {

}
