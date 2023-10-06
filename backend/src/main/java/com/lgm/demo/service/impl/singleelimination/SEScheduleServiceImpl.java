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
        // example: there may be 9 competitors, so in order to make a full round we need first to eliminate 1 competitor
        // which is 9 - 8 = 1 by formula, only first round may not be full
        // todo rename, mozda na first round?
        int numberOfMatchesInExtraRound = getNumberOfMatchesInExtraRound(competitors.size(), tournament.getNumberOfRounds());
        // matches are forming binary tree structure, using nodeNumber attribute
        List<Match> matches = createEmptyMatches(tournament);

        // since I am modifying competitors, I use this list to save that data and set it again to tournament
        List<Competitor> competitors2 = new ArrayList<>();

        Collections.shuffle(competitors);
        for(int i = 0; i < numberOfMatchesInExtraRound; i++){
            Competitor firstCompetitor = competitors.remove(0);
            Competitor secondCompetitor = competitors.remove(0);
            competitors2.add(firstCompetitor);
            competitors2.add(secondCompetitor);
            matches.get(i).setFirstCompetitor(firstCompetitor);
            matches.get(i).setSecondCompetitor(secondCompetitor);
        }

        int round = numberOfMatchesInExtraRound > 0 ? 2 : 1;
        int index = round == 2 ? (int)Math.pow(2, tournament.getNumberOfRounds()-1) : 0;

        int times = numberOfMatchesInExtraRound/2;
        index += times;
        numberOfMatchesInExtraRound -= 2*times;

        while(!competitors.isEmpty()){
            Competitor firstCompetitor = competitors.remove(0);
            Competitor secondCompetitor = null;

            if(numberOfMatchesInExtraRound == 0){
                secondCompetitor = competitors.remove(0);
                competitors2.add(secondCompetitor);
            }
            else if(numberOfMatchesInExtraRound == 1){
                numberOfMatchesInExtraRound = 0;
            }
            competitors2.add(firstCompetitor);
            matches.get(index).setFirstCompetitor(firstCompetitor);
            matches.get(index).setSecondCompetitor(secondCompetitor);
            index++;
        }

        tournament.setCompetitors(competitors2);
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
            matches.add(Match.builder().round(round).competition(tournament).nodeNumber(nodeNumber--).build());
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
