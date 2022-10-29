export class User{
    constructor(
        public username: string,
        public email: string,
        public token: string,
        public roles: string[],
        public id: number,
        public adminOfCompetitions: number[],
    ){}
}