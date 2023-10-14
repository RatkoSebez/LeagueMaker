package com.lgm.demo.service.impl.roudrobin;

import com.lgm.demo.model.*;
import com.lgm.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Qualifier("rr")
public class RRScheduleServiceImpl implements ScheduleService {

    @Override
    public Schedule createSchedule(Competition competition) {
        League league = (League) competition;
        List<Competitor> competitors = league.getCompetitors();
        int numberOfCompetitors = competitors.size();

        // algorithm
        // https://stackoverflow.com/questions/6648512/scheduling-algorithm-for-a-round-robin-tournament

        if(numberOfCompetitors % 2 == 1) numberOfCompetitors++;

        int numberOfRounds = (numberOfCompetitors - 1) * league.getTimesEachPlaysWithEach();
        int gamesPerRound = numberOfCompetitors / 2;
        league.setRounds(numberOfRounds);
        int roundsNeededToPlayOnceAgainstEveryone = numberOfRounds / league.getTimesEachPlaysWithEach();

        List<Competitor> firstRow = new ArrayList<>();
        List<Competitor> secondRow = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        LocalDate date = league.getCompetitionStart();
        int round = 1;
        while(numberOfRounds --> 0) {
            // this shuffles competitors so not same matches will repeat over and over if teams plays more than once
            // against each other
            if((numberOfRounds + 1) % roundsNeededToPlayOnceAgainstEveryone == 0)
                fillRowsRandomly(firstRow, secondRow, competitors);
            for(int i=0; i<gamesPerRound; i++) {
                Competitor firstRowCompetitor = firstRow.get(i);
                Competitor secondRowCompetitor = secondRow.get(i);
                if(firstRowCompetitor == null || secondRowCompetitor == null) continue;
                // point of this is to make home and away games
                if(numberOfRounds % 2 == 1)
                    matches.add(new Match(firstRowCompetitor, secondRowCompetitor, date, date, round, league, null));
                else
                    matches.add(new Match(secondRowCompetitor, firstRowCompetitor, date, date, round, league, null));
            }
            date = date.plusDays(league.getDaysBetweenMatches());
            round++;
            rotateArrays(firstRow, secondRow, gamesPerRound);
        }

        Collections.shuffle(matches);
        return Schedule.builder().competitionId(league.getId()).matches(matches).build();
    }

    private void rotateArrays(List<Competitor> firstRow, List<Competitor> secondRow, int gamesPerRound){
        Competitor lastInFirstRow = firstRow.get(gamesPerRound-1);
        Competitor firstInSecondRow = secondRow.get(0);
        for(int i=gamesPerRound-1; i>1; i--){
            firstRow.set(i, firstRow.get(i-1));
        }
        firstRow.set(1, firstInSecondRow);
        for(int i=0; i<gamesPerRound-1; i++){
            secondRow.set(i, secondRow.get(i+1));
        }
        secondRow.set(gamesPerRound-1, lastInFirstRow);
    }

    private void fillRowsRandomly(List<Competitor> firstRow, List<Competitor> secondRow, List<Competitor> competitors) {
        Collections.shuffle(competitors);
        firstRow.clear();
        secondRow.clear();

        for(int i=0; i<competitors.size(); i++){
            if(i < competitors.size() / 2) firstRow.add(competitors.get(i));
            else secondRow.add(competitors.get(i));
        }
        if(competitors.size() % 2 == 1) firstRow.add(null);
        Collections.shuffle(firstRow);
        Collections.shuffle(secondRow);
    }
}
