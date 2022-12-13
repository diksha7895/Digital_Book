import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const BOOK_URL = 'http://localhost:8081/digitalbooks';

const httpOptions = { headers: new HttpHeaders({'Content-Type' : 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private http: HttpClient, private tokenService: TokenStorageService) { }

  user=this.tokenService.getUser();

  createBook(book : any) : Observable<any>{
    return this.http.post(BOOK_URL + "/author/"+this.user.id+"/books",book,httpOptions);
  }

  updateBook(book : any) : Observable<any>{
    return this.http.put(BOOK_URL + "/author/"+this.user.id+"/updatebook/"+book.id,book,httpOptions);
  }

  getBooksCreatedByAuthor(id : any) : Observable<any>{
    return this.http.get(BOOK_URL + "/author/"+id+"/getAllBooks");
  }

  // blockBook(bookId : any,block : any) : {
  //   let params = new HttpParams();
  //   params = params.append("block",block);
  //   return this.http.post(BOOK_URL + "/author/"+this.user.id+"/books"+bookId,null,{params:params});
  // }
}
