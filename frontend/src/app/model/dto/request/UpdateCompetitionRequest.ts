export class UpdateCompetitionRequest{
    constructor(
        public competitionId: number,
        public name: string,
        public about: string,
        public rules: string,
    ){}
}