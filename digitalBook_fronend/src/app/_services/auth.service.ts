import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8081/digitalbooks/';
//const AUTH_API = 'https://c28vjkwqe3.execute-api.ap-northeast-1.amazonaws.com/UAT'; //for AWS

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' ,'Access-Control-Allow-Origin':"*"})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(userName: string, pwd: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      userName,
      pwd
    }, httpOptions);
  }

  register(userName: string, email: string, password: string, role: string): Observable<any> {
    console.log("role="+role);
    return this.http.post(AUTH_API + 'signup', {
      userName,
      email,
      password,
      role
    }, httpOptions);
  }
}
