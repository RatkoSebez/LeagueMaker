export class ChangePasswordRequest {
  constructor(public currentPassword: string, public newPassword: string) {}
}
