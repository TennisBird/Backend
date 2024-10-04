package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tennis_bird.core.entities.TeamEntity;

import javax.transaction.Transactional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE TeamEntity t SET t.name = :name WHERE t.id = :id")
    int changeName(
            @Param("name") String name,
            @Param("id") Long id
    );
}
