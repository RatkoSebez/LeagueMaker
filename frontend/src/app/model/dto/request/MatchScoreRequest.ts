export class MatchScoreRequest {
  constructor(
    public matchId: number,
    public firstCompetitorScore: number,
    public secondCompetitorScore: number
  ) {}
}
