export class Match{
    constructor(
        public id: number,
        public firstCompetitorId: number,
        public secondCompetitorId: number,
        public startTime: Date,
        public endTime: Date,
        public round: number,
        public result: string,
        public firstCompetitorScore: number,
        public secondCompetitorScore: number,
        public firstCompetitorName: string,
        public secondCompetitorName: string,
    ){}
}