package com.lgm.demo.service.impl;

import com.lgm.demo.model.*;
import com.lgm.demo.model.dto.response.CompetitorResponse;
import com.lgm.demo.model.enumeration.EResult;
import com.lgm.demo.model.exceptions.CompetitorNotFoundException;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.service.CompetitorService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompetitorServiceImpl implements CompetitorService {
    private final CompetitorRepository competitorRepository;

    public CompetitorServiceImpl(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    @Override
    public CompetitorResponse getCompetitor(Long competitorId) {
        Competitor competitor =
                competitorRepository.findById(competitorId).orElseThrow(() -> new CompetitorNotFoundException(competitorId));
        return CompetitorResponse.entityToDto(competitor);
    }

    @Override
    public List<Competitor> createEmptyCompetitors(Integer numberOfCompetitors) {
        List<Competitor> competitors = new ArrayList<>();
        for(int i=1; i<=numberOfCompetitors; i++)
            competitors.add(new Competitor(null, "team " + i, 0, 0, 0, 0, 0, 0, 0, null));
        return competitors;
    }

    @Override
    public void updateCompetitors(Match match) {
        calculateCompetitorsData(match,false);
    }

    @Override
    public void invalidateCompetitors(Match match) {
        calculateCompetitorsData(match, true);
    }

    private void calculateCompetitorsData(Match match, Boolean isAlreadyPlayed){
        Competitor firstCompetitor = match.getFirstCompetitor();
        Competitor secondCompetitor = match.getSecondCompetitor();
        int i = isAlreadyPlayed ? -1 : 1;
        int pointsWin = 0, pointsDraw = 0, pointsLose = 0;
        if(match.getCompetition() instanceof League){
            League league = (League) match.getCompetition();
            pointsWin = league.getPointsWin();
            pointsDraw = league.getPointsDraw();
            pointsLose = league.getPointsLose();
        }
        if(match.getResult() == EResult.DRAW){
            firstCompetitor.setGamesDraw(firstCompetitor.getGamesDraw() + i);
            firstCompetitor.setPoints(firstCompetitor.getPoints() + i * pointsDraw);
            firstCompetitor.setGamesPlayed(firstCompetitor.getGamesPlayed() + i);
            secondCompetitor.setGamesDraw(secondCompetitor.getGamesDraw() + i);
            secondCompetitor.setPoints(secondCompetitor.getPoints() + i * pointsDraw);
            secondCompetitor.setGamesPlayed(secondCompetitor.getGamesPlayed() + i);
        }
        if(match.getResult() == EResult.FIRST_WON){
            firstCompetitor.setGamesWon(firstCompetitor.getGamesWon() + i);
            firstCompetitor.setPoints(firstCompetitor.getPoints() + i * pointsWin);
            firstCompetitor.setGamesPlayed(firstCompetitor.getGamesPlayed() + i);
            secondCompetitor.setGamesLost(secondCompetitor.getGamesLost() + i);
            secondCompetitor.setPoints(secondCompetitor.getPoints() + i * pointsLose);
            secondCompetitor.setGamesPlayed(secondCompetitor.getGamesPlayed() + i);
        }
        if(match.getResult() == EResult.SECOND_WON){
            firstCompetitor.setGamesLost(firstCompetitor.getGamesLost() + i);
            firstCompetitor.setPoints(firstCompetitor.getPoints() + i * pointsLose);
            firstCompetitor.setGamesPlayed(firstCompetitor.getGamesPlayed() + i);
            secondCompetitor.setGamesWon(secondCompetitor.getGamesWon() + i);
            secondCompetitor.setPoints(secondCompetitor.getPoints() + i * pointsWin);
            secondCompetitor.setGamesPlayed(secondCompetitor.getGamesPlayed() + i);
        }
        if(isAlreadyPlayed){
            firstCompetitor.setScoreObtained(firstCompetitor.getScoreObtained() - match.getFirstCompetitorScore());
            firstCompetitor.setScoreConceded(firstCompetitor.getScoreConceded() - match.getSecondCompetitorScore());
            secondCompetitor.setScoreObtained(secondCompetitor.getScoreObtained() - match.getSecondCompetitorScore());
            secondCompetitor.setScoreConceded(secondCompetitor.getScoreConceded() - match.getFirstCompetitorScore());
        }
        else{
            firstCompetitor.setScoreObtained(firstCompetitor.getScoreObtained() + match.getFirstCompetitorScore());
            firstCompetitor.setScoreConceded(firstCompetitor.getScoreConceded() + match.getSecondCompetitorScore());
            secondCompetitor.setScoreObtained(secondCompetitor.getScoreObtained() + match.getSecondCompetitorScore());
            secondCompetitor.setScoreConceded(secondCompetitor.getScoreConceded() + match.getFirstCompetitorScore());
        }
        if(isAlreadyPlayed){
            List<Integer> last5games1 = firstCompetitor.getLast5games();
            last5games1.remove(last5games1.size()-1);
            List<Integer> last5games2 = secondCompetitor.getLast5games();
            last5games2.remove(last5games2.size()-1);
        }
        else{
            int gameCode = 0;
            if(match.getResult() == EResult.FIRST_WON) gameCode = 2;
            if(match.getResult() == EResult.DRAW) gameCode = 1;
            if(match.getResult() == EResult.SECOND_WON) gameCode = 0;
            List<Integer> last5games = firstCompetitor.getLast5games();
            last5games.add(gameCode);
            // remove first element and shift array left
            if(last5games.size() > 5){
                last5games.remove(0);
                for (int j=5; j>=0; j--)
                    last5games.set(i+1, last5games.get(i));
            }
            firstCompetitor.setLast5games(last5games);

            if(match.getResult() == EResult.FIRST_WON) gameCode = 0;
            if(match.getResult() == EResult.DRAW) gameCode = 1;
            if(match.getResult() == EResult.SECOND_WON) gameCode = 2;
            List<Integer> last5games2 = secondCompetitor.getLast5games();
            last5games2.add(gameCode);
            // remove first element and shift array left
            if(last5games2.size() > 5){
                last5games2.remove(0);
                for (int j=5; j>=0; j--)
                    last5games2.set(i+1, last5games2.get(i));
            }
            secondCompetitor.setLast5games(last5games2);
        }
        competitorRepository.save(firstCompetitor);
        competitorRepository.save(secondCompetitor);
    }
}
