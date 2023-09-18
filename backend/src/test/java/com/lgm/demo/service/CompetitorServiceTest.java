package com.lgm.demo.service;

import com.lgm.demo.model.Competitor;
import com.lgm.demo.repository.CompetitorRepository;
import com.lgm.demo.service.impl.CompetitorServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CompetitorServiceTest {
    @Mock
    private CompetitorRepository competitorRepository;

    @InjectMocks
    private CompetitorServiceImpl competitorService;

    @Test
    public void CreateEmptyCompetitors_CompetitorsSizeMatches(){
        int numberOfCompetitors = 10;
        List<Competitor> competitors = competitorService.createEmptyCompetitors(numberOfCompetitors);

        Assertions.assertThat(competitors.size()).isEqualTo(numberOfCompetitors);
    }

    @Test
    public void CreateEmptyCompetitors_CompetitorsNameMatches(){
        int numberOfCompetitors = 10;
        List<Competitor> competitors = competitorService.createEmptyCompetitors(numberOfCompetitors);

        int cnt = 1;
        for(Competitor competitor : competitors){
            Assertions.assertThat(competitor.getName()).isEqualTo("team " + cnt++);
        }
    }

    // TODO mozes iskoristiti za testiranje UpdateCompetitorNames
//    @Test
//    public void SetCompetitorName_NameIsReturned(){
//        String oldName = "oldName", newName = "newName";
//        Competitor competitor = Competitor.builder().name(oldName).build();
//        CompetitorRequest request = new CompetitorRequest(0L, newName);
//        when(competitorRepository.findById(request.getId())).thenReturn(Optional.ofNullable(competitor));
//
//        competitorService.updateCompetitorName(request);
//
//        Assertions.assertThat(competitor.getName()).isEqualTo(newName);
//    }

    // TODO mozes iskoristiti za testiranje UpdateCompetitorNames
//    @Test
//    public void SetCompetitorName_ThrowsExceptionWhenCompetitorIsNull(){
//        CompetitorRequest request = new CompetitorRequest(0L, "name");
//        when(competitorRepository.findById(request.getId())).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> competitorService.updateCompetitorName(request));
//    }
}
