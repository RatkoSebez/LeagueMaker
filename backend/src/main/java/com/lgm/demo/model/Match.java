package com.lgm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "first_competitor_id", referencedColumnName = "id")
    private Long firstCompetitorId;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "second_competitor_id", referencedColumnName = "id")
    private Long secondCompetitorId;

    // range when game should be played
    // if there is exact time for game, both dates will be same
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer round;
    @Enumerated(EnumType.STRING)
    private EResult result;
    private Integer firstCompetitorScore;
    private Integer secondCompetitorScore;
    private Long competitionId;

    public Match(Long firstCompetitorId, Long secondCompetitorId, LocalDate startTime, LocalDate endTime, Integer round, Long competitionId) {
        this.firstCompetitorId = firstCompetitorId;
        this.secondCompetitorId = secondCompetitorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.round = round;
        this.competitionId = competitionId;
    }
}
