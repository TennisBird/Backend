package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tennis_bird.core.entities.PersonEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Transactional
    @Query("SELECT p FROM PersonEntity p WHERE p.mailAddress = :mail")
    Optional<PersonEntity> findByMailAddress(@Param("mail") String mail);

    @Transactional
    @Query("SELECT p FROM PersonEntity p WHERE p.login = :login")
    Optional<PersonEntity> findByLogin(@Param("login") String login);


    // update can sucesfully update 0 rows!!!!!
    @Modifying
    @Transactional
    @Query("UPDATE PersonEntity p SET p.avatarPath =:avatar_path WHERE p.uuid = :id")
    void updateAvatar(@Param("avatar_path") String path, @Param("id") UUID id);
}
