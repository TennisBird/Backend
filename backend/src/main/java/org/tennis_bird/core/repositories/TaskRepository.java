package org.tennis_bird.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tennis_bird.core.entities.PersonEntity;
import org.tennis_bird.core.entities.TaskEntity;
import org.tennis_bird.core.entities.WorkerEntity;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Modifying
    @Query("UPDATE TaskEntity t " +
            "SET t.description = :description " +
            "WHERE t.code = :code")
    int addDescription(@Param("code") String code, @Param("description")  String description);

    @Modifying
    @Query("UPDATE TaskEntity t " +
            "SET t.author = :author_id " +
            "WHERE t.code = :code")
    int setAuthor(@Param("code") String code, @Param("author_id") Long authorId);

    @Modifying
    @Query("UPDATE TaskEntity t " +
            "SET t.status = :status " +
            "WHERE t.code = :code")
    int changeStatus(@Param("code") String code, @Param("status") String status);

    @Modifying
    @Query("UPDATE TaskEntity t " +
            "SET t.priority = :priority " +
            "WHERE t.code = :code")
    int changePriority(@Param("code") String code, @Param("priority") String priority);

    @Modifying
    @Query("UPDATE TaskEntity t " +
            "SET t.estimate = :estimate " +
            "WHERE t.code = :code")
    int setEstimate(@Param("code") String code, @Param("estimate") int estimate);
}
