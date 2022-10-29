export class LeagueStandings{
    constructor(
        public competitorId: number,
        public competitorName: string,
        public gamesPlayed: number,
        public gamesWon: number,
        public gamesDraw: number,
        public gamesLost: number,
        public points: number,
    ){}
}