import { CompetitorResponse } from './CompetitorResponse';

export class MatchResponse {
  constructor(
    public id: number,
    public firstCompetitor: CompetitorResponse,
    public secondCompetitor: CompetitorResponse,
    public startTime: Date,
    public endTime: Date,
    public round: number,
    public result: string,
    public firstCompetitorScore: number,
    public secondCompetitorScore: number,
    public matchNumber: number
  ) {}
}
