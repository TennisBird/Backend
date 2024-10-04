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
import java.util.Date;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.username = :username WHERE p.uuid = :uuid")
    int changeUsername(
            @Param("username") String username,
            @Param("uuid") UUID uuid
    );

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.firstName = :first_name WHERE p.uuid = :uuid")
    int changeFirstName(
            @Param("first_name") String firstName,
            @Param("uuid") UUID uuid
    );

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.lastName = :last_name WHERE p.uuid = :uuid")
    int changeLastName(
            @Param("last_name") String lastName,
            @Param("uuid") UUID uuid
    );

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.birthDate = :birth_date WHERE p.uuid = :uuid")
    int changeBirthDate(
            @Param("birth_date") Date birthDate,
            @Param("uuid") UUID uuid
    );

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.mailAddress = :mail_address WHERE p.uuid = :uuid")
    int changeMailAddress(
            @Param("mail_address") String mailAddress,
            @Param("uuid") UUID uuid
    );

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PersonEntity p SET p.telephoneNumber = :telephone_number WHERE p.uuid = :uuid")
    int changeTelephoneNumber(
            @Param("telephone_number") String telephoneNumber,
            @Param("uuid") UUID uuid
    );
}
