package com.lgm.demo.repository;

import com.lgm.demo.model.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitorRepository extends JpaRepository<Competitor, Long>{
}
