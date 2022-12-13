import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/digitalbooks';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  queryParam: "";
  flag=true;
  
  getPublicContent() {
    throw new Error('Method not implemented.');
  }
  constructor(private http: HttpClient) { }

  getSearchBookResult(search : any) : Observable<any> {
    this.queryParam="";
    this.flag=true;
    if(search.title!=""){
        if(this.flag){
          this.queryParam = '?';
        }else{
          this.queryParam +='&';
        }
      this.queryParam +='title='+search.category;
        this.flag=false;
    }
    // let query = new HttpParams();
    // query = query.append("category",category).append("title",title).append("author",author)
    //         .append("price",price).append("publisher",publisher);

        return this.http.get(API_URL + 'search' + this.queryParam,{responseType : 'text'});


  }
  subscribeBook(){
    //need to be implemented
  }

  getAllSubscribedBooks(id : any) : Observable<any> {
    return this.http.get(API_URL + '/reader/'+id+'/books');
  }

  getBooksCreatedByAuthor(id : any): Observable<any> {
    return this.http.get(API_URL + '/author/'+id+'/getAllBooks');
  }

}
