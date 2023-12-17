package com.example.HomeWorkNotePostgres.repository;

import com.example.HomeWorkNotePostgres.entity.NoteEntity;
import com.example.HomeWorkNotePostgres.projection.NoteWithUserNameProj;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT n.id AS noteId, n.title, n.content, u.username " +
                    "FROM note n LEFT JOIN users u ON u.id = n.user_id " +
                    "WHERE u.id = :userId")
    List<NoteWithUserNameProj> findWithUsername(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"user"})
    @Query("FROM NoteEntity ne WHERE ne.user.id = :userId")
    List<NoteEntity> findWithUser(@Param("userId") Long userId);
    @EntityGraph(attributePaths = {"user", "user.profile"})
    @Query("FROM NoteEntity ne WHERE ne.title = :title")
    Optional<NoteEntity> findByTitle (@Param("title") String title);
}
