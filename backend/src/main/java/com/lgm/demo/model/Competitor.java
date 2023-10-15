package com.lgm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Competitor implements Comparable<Competitor>{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesDraw;
    private Integer gamesLost;
    private Integer points;
    private Integer scoreObtained;
    private Integer scoreConceded;
    // 0 - lose | 1 - draw | 2 - win
    @ElementCollection
    private List<Integer> last5games = new ArrayList<>();

    @Override
    public int compareTo(Competitor o){
        // firstly I compare number of points
        int ans = o.points.compareTo(points);
        if(ans != 0) return ans;
        // then score difference
        ans = (o.scoreObtained - o.scoreConceded) - (scoreObtained - scoreConceded);
        if(ans != 0) return ans;
        // then who has more score obtained
        ans = o.scoreObtained.compareTo(scoreObtained);
        if(ans != 0) return ans;
        // then number of losses (one with more losses is below)
        ans = gamesLost.compareTo(o.gamesLost);
        if(ans != 0) return ans;
        // after that number of draws (one with more draws is below)
        ans = gamesDraw.compareTo(o.gamesDraw);
        if(ans != 0) return ans;
        // if everything else is equal, I am sorting competitors by name
        ans = name.compareTo(o.name);
        return ans;
    }
}
