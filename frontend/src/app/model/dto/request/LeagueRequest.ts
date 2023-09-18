export class LeagueRequest {
  constructor(
    public numberOfCompetitors: number,
    public timesEachPlaysWithEach: number,
    public competitionStart: Date,
    public daysBetweenMatches: number,
    public leagueName: string,
    public pointsWin: number,
    public pointsDraw: number,
    public pointsLose: number
  ) {}
}
