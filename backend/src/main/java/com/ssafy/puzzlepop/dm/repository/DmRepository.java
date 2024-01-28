package com.ssafy.puzzlepop.dm.repository;

import com.ssafy.puzzlepop.dm.domain.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmRepository extends JpaRepository<Dm, Integer> {


}
