package com.lgm.demo.service;

import com.lgm.demo.model.Competition;
import com.lgm.demo.model.League;
import com.lgm.demo.model.Tournament;
import com.lgm.demo.model.dto.response.CompetitionResponse;
import com.lgm.demo.exception.CompetitionNotFoundException;
import com.lgm.demo.repository.CompetitionRepository;
import com.lgm.demo.service.impl.CompetitionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompetitionServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private CompetitionServiceImpl competitionService;

    @Test
    public void GetCompetition_ThrowsExceptionWhenCompetitionDoesNotExists(){
        Long competitionId = 1L;
        when(competitionRepository.getCompetitionById(competitionId)).thenReturn(Optional.empty());

        assertThrows(CompetitionNotFoundException.class, () -> competitionService.getCompetition(competitionId));
    }

    @Test
    public void GetCompetition_ReturnsDataWhenCompetitionExists(){
        Long competitionId = 1L;
        when(competitionRepository.getCompetitionById(competitionId)).thenReturn(Optional.of(new Competition()));

        Competition competition = competitionService.getCompetition(competitionId);

        org.assertj.core.api.Assertions.assertThat(competition).isNotNull();
    }

    @Test
    public void GetCompetitionNames_ResponseIsReturned(){
        String request = "1&3";
        when(competitionRepository.getCompetitionById(1L)).thenReturn(Optional.of(new League()));
        when(competitionRepository.getCompetitionById(3L)).thenReturn(Optional.of(new Tournament()));

        List<CompetitionResponse> response = competitionService.getCompetitionNames(request);

        org.assertj.core.api.Assertions.assertThat(response.size()).isEqualTo(2);
    }
}
