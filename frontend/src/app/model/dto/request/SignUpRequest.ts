export class SignUpRequest {
  constructor(
    public name: string,
    public surname: string,
    public email: string,
    public username: string,
    public password: string,
    public sex: number
  ) {}
}
