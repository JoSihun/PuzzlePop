package com.ssafy.puzzlepop.dm.repository;

import com.ssafy.puzzlepop.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmRepository extends JpaRepository<Image, Integer> {


}
