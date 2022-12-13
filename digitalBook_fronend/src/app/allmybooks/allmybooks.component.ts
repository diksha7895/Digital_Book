import { Component, OnInit } from '@angular/core';
import { Book } from '../_model/book';
import { HttpClientService } from '../_services/http-client-service.service';

@Component({
  selector: 'app-allmybooks',
  templateUrl: './allmybooks.component.html',
  styleUrls: ['./allmybooks.component.css']
})

export class AllmybooksComponent implements OnInit {

  books: Array<Book>;

  constructor(private httpClientService: HttpClientService) { }

  
  ngOnInit() {

    this.httpClientService.getBooks().subscribe(
      response => this.handleSuccessfulResponse(response)
    );
  }

  handleSuccessfulResponse(response:any) {
    this.books = response;
  }
}
