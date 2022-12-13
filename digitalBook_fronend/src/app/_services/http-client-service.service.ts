import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Book } from '../_model/book';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private httpClient: HttpClient) {
  }
  getBooks() {
    return this.httpClient.get<Book[]>('http://localhost:8081/digitalbooks/get');
  }
}
