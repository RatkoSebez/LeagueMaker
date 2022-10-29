package com.lgm.demo.service.impl;

import com.lgm.demo.model.*;
import com.lgm.demo.repository.ScheduleRepository;
import com.lgm.demo.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public RoundRobinSchedule createRoundRobinSchedule(League league) {
        RoundRobinSchedule schedule = new RoundRobinSchedule();
        schedule.setCompetitionId(league.getId());
        List<Match> matches = new ArrayList<>();
        List<Competitor> competitors = league.getCompetitors();
        int numberOfCompetitors = competitors.size();

        // algorithm
        // https://stackoverflow.com/questions/6648512/scheduling-algorithm-for-a-round-robin-tournament

        List<Long> firstRow = new ArrayList<>();
        List<Long> secondRow = new ArrayList<>();

        fillRowsRandomly(firstRow, secondRow, competitors);
        if(numberOfCompetitors % 2 == 1) numberOfCompetitors++;

        int numberOfRounds = (numberOfCompetitors - 1) * league.getTimesEachPlaysWithEach();
        int gamesPerRound = numberOfCompetitors / 2;
        league.setRounds(numberOfRounds);
        int roundsOnceAgainstEveryone = numberOfRounds / league.getTimesEachPlaysWithEach();

        LocalDate date = league.getCompetitionStart();
        int round = 1;
        while(numberOfRounds --> 0) {
            // this shuffles competitors so not same matches will repeat over and over if teams plays more than once
            // against each other
            if(numberOfRounds % roundsOnceAgainstEveryone == 0) fillRowsRandomly(firstRow, secondRow, competitors);
            for (int i = 0; i < gamesPerRound; i++) {
                Long firstRowId = firstRow.get(i);
                Long secondRowId = secondRow.get(i);
                if(Objects.equals(firstRowId, -1L) || Objects.equals(secondRowId, -1L)) continue;
                // point of this is to make home and away games
                if(numberOfRounds % 2 == 1)
                    matches.add(new Match(firstRowId, secondRowId, date, date, round, league.getId()));
                else
                    matches.add(new Match(secondRowId, firstRowId, date, date, round, league.getId()));
            }
            date = date.plusDays(league.getDaysBetweenMatches());
            round++;
            rotateArrays(firstRow, secondRow, gamesPerRound);
        }

        Collections.shuffle(matches);

        schedule.setMatches(matches);
        return schedule;
    }

    @Override
    public Schedule getSchedule(Long competitionId) {
        return scheduleRepository.getScheduleByCompetitionId(competitionId);
    }

    private void rotateArrays(List<Long> firstRow, List<Long> secondRow, int gamesPerRound){
        Long lastInFirstRow = firstRow.get(gamesPerRound-1);
        Long firstInSecondRow = secondRow.get(0);
        for(int i=gamesPerRound-1; i>1; i--){
            firstRow.set(i, firstRow.get(i-1));
        }
        firstRow.set(1, firstInSecondRow);
        for(int i=0; i<gamesPerRound-1; i++){
            secondRow.set(i, secondRow.get(i+1));
        }
        secondRow.set(gamesPerRound-1, lastInFirstRow);
    }

    private void fillRowsRandomly(List<Long> firstRow, List<Long> secondRow, List<Competitor> competitors) {
        firstRow.clear();
        secondRow.clear();

        for (int i = 0; i < competitors.size(); i++) {
            Long competitorId = competitors.get(i).getId();
            if (i < competitors.size() / 2) firstRow.add(competitorId);
            else secondRow.add(competitorId);
        }
        if(competitors.size() % 2 == 1) firstRow.add(-1L);
        Collections.shuffle(firstRow);
        Collections.shuffle(secondRow);
    }
}
