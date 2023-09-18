export class CompetitionResponse {
  constructor(
    public id: number = 0,
    public name: string = '',
    public about: string = '',
    public rules: string = '',
    public numberOfCompetitors: number = 0,
    public type: string = ''
  ) {}
}
