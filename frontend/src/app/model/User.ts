export class User{
    constructor(
        public username: string = "",
        public email: string = "",
        public token: string = "",
        public roles: string[] = [],
        public id: number = 0,
        public adminOfCompetitions: number[] = [],
        public profileImageInBase64: string = "",
        public bio: string = "",
        public sex: string = "",
        public name: string = "",
        public surname: string = "",
    ){}
}