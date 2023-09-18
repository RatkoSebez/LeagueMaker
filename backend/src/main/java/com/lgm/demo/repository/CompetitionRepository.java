package com.lgm.demo.repository;

import com.lgm.demo.model.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Competition> getCompetitionById(Long id);

    @Query("SELECT c " +
            "from Competition c " +
            "where c.name like %:query%")
    Page<Competition> findSimilarSearchResults(@Param("query") String query, Pageable pageable);

    @Query("SELECT c " +
            "from Competition c " +
            "where c.name = :query")
    Page<Competition> findMatchingSearchResults(@Param("query") String query, Pageable pageable);
}
