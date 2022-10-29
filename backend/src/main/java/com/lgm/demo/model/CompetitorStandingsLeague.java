package com.lgm.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CompetitorStandingsLeague {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long competitorId;

    private Long competitionnId;

    private Integer gamesPlayed;

    private Integer gamesWon;

    private Integer gamesDraw;

    private Integer gamesLost;

    private Integer points;
}
