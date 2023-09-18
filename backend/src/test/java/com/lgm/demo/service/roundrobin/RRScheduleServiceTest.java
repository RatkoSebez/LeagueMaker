package com.lgm.demo.service.roundrobin;

import com.lgm.demo.model.Competitor;
import com.lgm.demo.model.League;
import com.lgm.demo.model.Match;
import com.lgm.demo.model.Schedule;
import com.lgm.demo.service.impl.roudrobin.RRScheduleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class RRScheduleServiceTest {

    @InjectMocks
    private RRScheduleServiceImpl scheduleService;

    private final List<League> leagues = new ArrayList<>();

    @BeforeEach
    public void init(){
        List<Competitor> competitors1 = new ArrayList<>();
        for(int i=0; i<12; i++)
            competitors1.add(new Competitor((long)i, "team " + i, 0, 0, 0, 0, 0, 0, 0, null));
        List<Competitor> competitors3 = new ArrayList<>();
        for(int i=0; i<11; i++)
            competitors3.add(new Competitor((long)i, "team " + i, 0, 0, 0, 0, 0, 0, 0, null));

        leagues.add(new League(
                null,
                competitors1,
                null,
                competitors1.size(),
                "league name",
                2,
                3,
                1,
                0,
                null,
                LocalDate.now(),
                7
        ));

        leagues.add(new League(
                null,
                competitors3,
                null,
                competitors3.size(),
                "league name",
                1,
                3,
                1,
                0,
                null,
                LocalDate.now(),
                7
        ));

        leagues.add(new League(
                null,
                competitors3,
                null,
                competitors3.size(),
                "league name",
                3,
                3,
                1,
                0,
                null,
                LocalDate.now(),
                7
        ));
    }

    @Test
    public void CreateSchedule_NumberOfMatchesInScheduleIsCorrect(){
        for(League league : leagues){
            Schedule schedule = scheduleService.createSchedule(league);

            int n = league.getNumberOfCompetitors();
            int expectedNumberOfMatches = (n * (n - 1) / 2) * league.getTimesEachPlaysWithEach();
            Assertions.assertThat(schedule.getMatches().size()).isEqualTo(expectedNumberOfMatches);
        }
    }

    @Test
    public void CreateSchedule_EveryCompetitorPlaysRightAmountOfMatches(){
        for(League league : leagues){
            Schedule schedule = scheduleService.createSchedule(league);

            List<Match> matches = schedule.getMatches();
            Map<Long, Integer> map = new HashMap<>();

            for(Match match : matches){
                long id1 = match.getFirstCompetitor().getId();
                long id2 = match.getSecondCompetitor().getId();
                if(!map.containsKey(id1))
                    map.put(id1, 0);
                if(!map.containsKey(id2))
                    map.put(id2, 0);
                map.put(id1, map.get(id1) + 1);
                map.put(id2, map.get(id2) + 1);
            }

            int expectedNumberOfMatches = (league.getNumberOfCompetitors() - 1) * league.getTimesEachPlaysWithEach();
            for(Competitor competitor : league.getCompetitors()) {
                Assertions.assertThat(map.get(competitor.getId())).isEqualTo(expectedNumberOfMatches);
            }
        }
    }

    @Test
    public void CreateSchedule_ScheduleIdIsCorrect(){
        for(League league : leagues){
            Schedule schedule = scheduleService.createSchedule(league);

            Assertions.assertThat(schedule.getCompetitionId()).isEqualTo(league.getId());
        }
    }

    @Test
    public void CreateSchedule_EverybodyPlaysAtMostOneMatchInEachRound(){
        for(League league : leagues){
            Schedule schedule = scheduleService.createSchedule(league);

            List<Match> matches = schedule.getMatches();
            Map<Integer, List<Long>> map = new HashMap<>();

            for(Match match : matches){
                long id1 = match.getFirstCompetitor().getId();
                long id2 = match.getSecondCompetitor().getId();
                int round = match.getRound();
                if(!map.containsKey(round))
                    map.put(round, new ArrayList<>());
                List<Long> list = map.get(round);
                list.add(id1);
                list.add(id2);
                map.put(round, list);
            }

            for(int i=1; i<=league.getRounds(); i++){
                List<Long> ids = map.get(i);
                Collections.sort(ids);
                for(int j=1; j<ids.size(); j++){
                    Assertions.assertThat(ids.get(j)).isNotEqualTo(ids.get(j-1));
                }
            }
        }
    }
}
