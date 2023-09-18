package com.lgm.demo.service.singleelimination;

import com.lgm.demo.model.*;
import com.lgm.demo.service.impl.singleelimination.SEScheduleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SEScheduleServiceTest {

    @InjectMocks
    private SEScheduleServiceImpl scheduleService;

    private final List<Tournament> tournaments = new ArrayList<>();

    @BeforeEach
    public void init(){
        List<Competitor> competitors1 = new ArrayList<>();
        for(int i=0; i<12; i++)
            competitors1.add(new Competitor((long)i, "team " + i, 0, 0, 0, 0, 0, 0, 0, null));
        List<Competitor> competitors2 = new ArrayList<>();
        for(int i=0; i<32; i++)
            competitors2.add(new Competitor((long)i, "team " + i, 0, 0, 0, 0, 0, 0, 0, null));

        tournaments.add(new Tournament(1L, competitors1,
                null, competitors1.size(),
                "tournament name",
                (int) Math.ceil(Math.log(competitors1.size()) / Math.log(2))));
        tournaments.add(new Tournament(1L, competitors2,
                null, competitors2.size(),
                "tournament name",
                (int) Math.ceil(Math.log(competitors2.size()) / Math.log(2))));
    }

    @Test
    public void CreateSchedule_NumberOfMatchesInScheduleIsCorrect(){
        for(Tournament tournament : tournaments){
            Schedule schedule = scheduleService.createSchedule(tournament);

            int expectedNumberOfMatches = (int) Math.pow(2, tournament.getNumberOfRounds()) - 1;
            Assertions.assertThat(schedule.getMatches().size()).isEqualTo(expectedNumberOfMatches);
        }
    }

    @Test
    public void CreateSchedule_MatchesAreNumberedFrom1ToNStartingFromFinal(){
        for(Tournament tournament : tournaments){
            Schedule schedule = scheduleService.createSchedule(tournament);

            List<Match> matches = schedule.getMatches();
            for(int i=0; i<matches.size(); i++)
                Assertions.assertThat(matches.get(i).getMatchNumber()).isEqualTo(matches.size()-i);
        }
    }

    @Test
    public void CreateSchedule_EveryCompetitorPlaysOneMatch(){
        for(Tournament tournament : tournaments){
            Schedule schedule = scheduleService.createSchedule(tournament);

            List<Match> matches = schedule.getMatches();
            List<Competitor> competitors = tournament.getCompetitors();
            Map<Long, Boolean> map = new HashMap<>();
            for(Match match : matches){
                if(match.getFirstCompetitor() != null)
                    map.put(match.getFirstCompetitor().getId(), true);
                if(match.getSecondCompetitor() != null)
                    map.put(match.getSecondCompetitor().getId(), true);
            }

            for(Competitor competitor : competitors)
                Assertions.assertThat(map.get(competitor.getId())).isTrue();
        }
    }

    @Test
    public void CreateSchedule_ScheduleIdIsCorrect(){
        for(Tournament tournament : tournaments){
            Schedule schedule = scheduleService.createSchedule(tournament);

            Assertions.assertThat(schedule.getCompetitionId()).isEqualTo(tournament.getId());
        }
    }

    @Test
    public void CreateSchedule_CompetitorsAreNotChanged(){
        for(Tournament tournament : tournaments) {
            Integer competitorsSize = tournament.getCompetitors().size();
            scheduleService.createSchedule(tournament);

            Assertions.assertThat(competitorsSize).isEqualTo(tournament.getCompetitors().size());
        }
    }
}
