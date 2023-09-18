package com.lgm.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lgm.demo.model.enumeration.EResult;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Match implements Comparable<Match>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "firstcompetitor_id", referencedColumnName = "id")
    private Competitor firstCompetitor;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "secondcompetitor_id", referencedColumnName = "id")
    private Competitor secondCompetitor;
    // range when game should be played
    // if there is exact time for game, both dates will be same
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer round;
    @Enumerated(EnumType.STRING)
    private EResult result;
    private Integer firstCompetitorScore;
    private Integer secondCompetitorScore;
    private Boolean isFinished = false;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "competition", referencedColumnName = "id")
    private Competition competition;
    private Integer matchNumber;

    public Match(Competitor firstCompetitor, Competitor secondCompetitor, LocalDate startTime, LocalDate endTime, Integer round, Competition competition, Integer matchNumber) {
        this.firstCompetitor = firstCompetitor;
        this.secondCompetitor = secondCompetitor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.round = round;
        this.competition = competition;
        this.matchNumber = matchNumber;
    }

    @Override
    public int compareTo(Match o) {
        if(this.matchNumber == null)
            return this.id.compareTo(o.id);
        return this.matchNumber - o.matchNumber;
//        if(Objects.equals(this.matchNumber, o.matchNumber))
//            return 0;
//        else if(this.matchNumber > o.matchNumber)
//            return 1;
//        else
//            return -1;
    }
}
