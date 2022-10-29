import { Injectable } from '@angular/core';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  saveToLocalStorage(data: any){
    localStorage.setItem("username", (data as User).username)
    localStorage.setItem("email", (data as User).email)
    localStorage.setItem("token", (data as User).token)
    localStorage.setItem("roles", JSON.stringify((data as User).roles))
    localStorage.setItem("id", JSON.stringify((data as User).id))
    localStorage.setItem("adminOfCompetitions", JSON.stringify((data as User).adminOfCompetitions))
  }

  getAdminOfCompetitions(){
    return JSON.parse(localStorage.getItem("adminOfCompetitions") || "")
  }
}
