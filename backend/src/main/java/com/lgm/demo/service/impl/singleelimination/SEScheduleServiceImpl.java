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
public class SEScheduleServiceImpl implements ScheduleService{

    @Override
    public Schedule createSchedule(Competition competition){
        Tournament tournament = (Tournament)competition;
        List<Competitor> competitors = tournament.getCompetitors();
        int numberOfMatchesInExtraRound = getNumberOfMatchesInExtraRound(competitors.size(), tournament.getNumberOfRounds());
        List<Match> matches = createEmptyMatches(tournament);
        int competitorIndex = 0;

        Collections.shuffle(competitors);
        for(int i = 0; i < numberOfMatchesInExtraRound; i++){
            matches.get(i).setFirstCompetitor(competitors.get(competitorIndex++));
            matches.get(i).setSecondCompetitor(competitors.get(competitorIndex++));
        }

        int round = numberOfMatchesInExtraRound > 0 ? 2 : 1;
        int matchIndex = round == 2 ? (int)Math.pow(2, tournament.getNumberOfRounds()-1) : 0;
        matchIndex += numberOfMatchesInExtraRound/2;
        numberOfMatchesInExtraRound = numberOfMatchesInExtraRound % 2 == 0 ? 0 : 1;

        while(competitorIndex < competitors.size()){
            Competitor secondCompetitor = numberOfMatchesInExtraRound == 0 ? competitors
                    .get(competitorIndex++) : null;
            if(numberOfMatchesInExtraRound == 1) numberOfMatchesInExtraRound = 0;
            matches.get(matchIndex).setFirstCompetitor(competitors.get(competitorIndex++));
            matches.get(matchIndex++).setSecondCompetitor(secondCompetitor);
        }

        return Schedule.builder().competitionId(tournament.getId()).matches(matches).build();
    }

    private boolean isPowerOfTwo(int n){
        double v = Math.log(n)/Math.log(2);
        return (int)(Math.ceil(v)) == (int)(Math.floor(v));
    }

    private List<Match> createEmptyMatches(Tournament tournament){
        List<Match> matches = new ArrayList<>();
        int numberOfRounds = tournament.getNumberOfRounds(), round = 1;
        int nodeNumber = matchesInRound(numberOfRounds+1)-1;
        int matchesInRound = matchesInRound(numberOfRounds--);

        while(matchesInRound-- > 0){
            matches.add(Match.builder().round(round).competition(tournament)
                    .nodeNumber(nodeNumber--).build());
            if(matchesInRound == 0 && numberOfRounds > 0){
                matchesInRound = matchesInRound(numberOfRounds--);
                round++;
            }
        }
        return matches;
    }

    private int matchesInRound(int round){
        return (int)Math.pow(2, round-1);
    }

    private int getNumberOfMatchesInExtraRound(int numberOfCompetitors, int numberOfRounds){
        return isPowerOfTwo(numberOfCompetitors) ? 0 : numberOfCompetitors-matchesInRound(numberOfRounds);
    }
}
