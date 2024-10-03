package org.tennis_bird.core.repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.services.PersonService;

import javax.transaction.Transactional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.username = :username WHERE p.uuid = :uuid")
    int changeUsername(
            @Param("username") String username,
            @Param("uuid") UUID uuid
    );
}
