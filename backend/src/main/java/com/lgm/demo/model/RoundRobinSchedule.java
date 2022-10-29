package com.lgm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RoundRobinSchedule extends Schedule{
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private List<Match> matches = new ArrayList<>();

    public RoundRobinSchedule(Long id, Long competitionId, List<Match> matches) {
        super(id, competitionId);
        this.matches = matches;
    }
}
