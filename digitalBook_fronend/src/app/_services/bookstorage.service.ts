import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookstorageService {

  book : Book ={
    id : null,
    logo : null,
    title : null,
    category : null,
    authorId : null,
    authorName : null,
    price : null,
    content : null,
    publisher : null,
    publishedDate : null,
    active : null
  }
  constructor() { }

  getBook(){
    return this.book;
  }
  setBook(book : any){
    this.book = book;
  }
}
  export class Book {
    id = null;
    logo = null;
    title = null;
    category = null;
    authorId = null;
    authorName = null;
    price = null;
    content = null;
    publisher = null;
    publishedDate = null;
    active = null
  }

