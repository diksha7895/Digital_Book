import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Book } from '../_model/book.model';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private httpClient: HttpClient) {
  }
  getBooks() {
    return this.httpClient.get<Book[]>('http://localhost:8082/digitalbooks/get');
  }
}
