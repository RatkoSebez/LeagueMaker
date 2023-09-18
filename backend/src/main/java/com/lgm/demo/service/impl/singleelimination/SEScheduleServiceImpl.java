package com.lgm.demo.service.impl.singleelimination;

import com.lgm.demo.model.*;
import com.lgm.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Qualifier("se")
public class SEScheduleServiceImpl implements ScheduleService {

    @Override
    public Schedule createSchedule(Competition competition) {
        Tournament tournament = (Tournament) competition;
        List<Competitor> competitors = tournament.getCompetitors();
        int numberOfCompetitors = competitors.size();
        int numberOfRounds = tournament.getNumberOfRounds();
        // example: there may be 9 competitors, so in order to make a full round we need first to eliminate 1 competitor
        // which is 9 - 8 = 1 by formula, only first round may not be full
        int numberOfMatchesInExtraRound = calculateNumberOfMatchesInExtraRound(numberOfCompetitors, numberOfRounds);

        List<Match> matches = createEmptyMatches(numberOfRounds, tournament);

        // since I am modifying competitors, I use this list to save that data and set it again to tournament
        List<Competitor> competitors2 = new ArrayList<>();

        Collections.shuffle(competitors);
        for(int i=0; i<numberOfMatchesInExtraRound; i++){
            Competitor firstCompetitor = competitors.remove(0);
            Competitor secondCompetitor = competitors.remove(0);
            competitors2.add(firstCompetitor);
            competitors2.add(secondCompetitor);
            matches.get(i).setFirstCompetitor(firstCompetitor);
            matches.get(i).setSecondCompetitor(secondCompetitor);
        }

        int round = numberOfMatchesInExtraRound > 0 ? 2 : 1;
        int i = 0;
        if(round == 2)
            i = (int) Math.pow(2, numberOfRounds - 1);

        while(competitors.size() > 0){
            Competitor firstCompetitor;
            Competitor secondCompetitor = null;

            if(numberOfMatchesInExtraRound >= 2){
                numberOfMatchesInExtraRound -= 2;
                i++;
                continue;
            }
            else if(numberOfMatchesInExtraRound == 0) {
                secondCompetitor = competitors.remove(0);
                competitors2.add(secondCompetitor);
            }
            else if(numberOfMatchesInExtraRound == 1){
                numberOfMatchesInExtraRound--;
            }
            firstCompetitor = competitors.remove(0);
            competitors2.add(firstCompetitor);
            matches.get(i).setFirstCompetitor(firstCompetitor);
            matches.get(i).setSecondCompetitor(secondCompetitor);
            i++;
        }

        tournament.setCompetitors(competitors2);
        Schedule schedule = Schedule.builder().competitionId(tournament.getId()).matches(matches).build();
        System.out.println(schedule.toString());
        return schedule;
    }

    private boolean isPowerOfTwo(int n){
        double v = Math.log(n) / Math.log(2);
        return (int)(Math.ceil(v)) == (int)(Math.floor(v));
    }

    private List<Match> createEmptyMatches(Integer numberOfRounds, Tournament tournament){
        int matchesInRound = matchesInRound(numberOfRounds - 1);
        int matchNumber = matchesInRound(numberOfRounds) - 1, round = 1;
        numberOfRounds--;

        List<Match> matches = new ArrayList<>();
        while(matchesInRound --> 0){
            matches.add(Match.builder()
                    .round(round)
                    .competition(tournament)
                    .matchNumber(matchNumber--).build());
            if(matchesInRound == 0 && numberOfRounds > 0){
                matchesInRound = matchesInRound(numberOfRounds-- - 1);
                round++;
            }
        }

        return matches;
    }

    private int matchesInRound(int round){
        return (int) Math.pow(2, round);
    }

    private int calculateNumberOfMatchesInExtraRound(int numberOfCompetitors, int numberOfRounds){
        int numberOfMatchesInExtraRound = numberOfCompetitors - matchesInRound(numberOfRounds - 1);
        if(isPowerOfTwo(numberOfCompetitors))
            numberOfMatchesInExtraRound = 0;
        return numberOfMatchesInExtraRound;
    }
}
