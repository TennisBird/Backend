package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tennis_bird.core.entities.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
