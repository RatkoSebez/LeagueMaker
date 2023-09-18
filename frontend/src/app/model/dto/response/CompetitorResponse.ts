export class CompetitorResponse {
  constructor(
    public id: number = 0,
    public name: string = '',
    public gamesPlayed: number = 0,
    public gamesWon: number = 0,
    public gamesDraw: number = 0,
    public gamesLost: number = 0,
    public points: number = 0,
    public scoreObtained: number = 0,
    public scoreConceded: number = 0,
    public last5games: number[] = []
  ) {}
}
