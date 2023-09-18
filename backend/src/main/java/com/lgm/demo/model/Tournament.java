package com.lgm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Tournament extends Competition {
    // vidi da li bi mogao napraviti da se moze igrati vise meceva (npr. 2 kao liga sampiona)
    // private Integer timesEachPlaysWithEach;
    private Integer numberOfRounds;

    public Tournament(Long id, List<Competitor> competitors, Schedule schedule, Integer numberOfCompetitors, String name, Integer numberOfRounds) {
        super(id, competitors, schedule, numberOfCompetitors, name, "", "");
        this.numberOfRounds = numberOfRounds;
    }
}
