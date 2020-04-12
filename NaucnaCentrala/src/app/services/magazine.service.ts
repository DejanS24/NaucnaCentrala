import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Router} from '@angular/router';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  getMagazineForm(){
    return this.httpClient.get("http://localhost:8070/magazine/getForm") as Observable<any>;
  }

  createMagazine(user, taskId) {
    return this.httpClient.post("http://localhost:8070/magazine/fillForm/".concat(taskId), user) as Observable<any>;
  }

  getAllMagazines(){
    return this.httpClient.get("http://localhost:8070/magazine/all") as Observable<any>;
  }
}
