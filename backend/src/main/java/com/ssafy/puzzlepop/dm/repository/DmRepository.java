package com.ssafy.puzzlepop.dm.repository;

import com.ssafy.puzzlepop.dm.domain.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DmRepository extends JpaRepository<Dm, Long> {

    @Query("SELECT d FROM Dm d " +
            "WHERE (d.fromUserId = :userId AND d.toUserId = :friendUserId) OR " +
            "(d.fromUserId = :friendUserId AND d.toUserId = :userId) ")
    List<Dm> getDmsByuserIdAndFriendUserId(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId);
}