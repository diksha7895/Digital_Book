import { Component, OnInit } from '@angular/core';
import { Book, BookstorageService } from '../_services/bookstorage.service';


@Component({
  selector: 'app-bookinfo',
  templateUrl: './bookinfo.component.html',
  styleUrls: ['./bookinfo.component.css']
})
export class BookinfoComponent implements OnInit {

   book : any;
   

  constructor(private bookService: BookstorageService) { }

  ngOnInit():void{
    this.book = this.bookService.getBook();
    console.log("book info : " +this.book);
  }

  // ngOnInit() {
  //   this.httpClientService.getBooks().subscribe(response => this.handleSuccessfulResponse(response));
  // }

  // handleSuccessfulResponse(response: Book[]){
  //   this.books=response;
  // }
}
