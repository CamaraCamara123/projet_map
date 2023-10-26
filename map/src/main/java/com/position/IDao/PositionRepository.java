package com.position.IDao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.position.entity.Position;

public interface PositionRepository extends JpaRepository<Position,Integer>{
    
}
