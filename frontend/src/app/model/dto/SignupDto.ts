export class SignupDto{
    constructor(
        public name: string,
        public surname: string,
        public email: string,
        public username: string,
        public password: string,
    ){}
}