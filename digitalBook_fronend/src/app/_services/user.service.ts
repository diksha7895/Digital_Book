import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const API_URL = 'http://localhost:8081/digitalbooks';
//const API_URL = 'https://c28vjkwqe3.execute-api.ap-northeast-1.amazonaws.com/UAT'; //for AWS

const headers = new HttpHeaders({'Content-Type' : 'application/json',
'Access-Control-Allow-Origin' : "*"});

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  getPublicContent() {
    throw new Error('Method not implemented.');
  }
  constructor(private http: HttpClient,private tokenStorageService : TokenStorageService) { }

 search(category : any,title : any,author : any) : Observable<any> {
     let queryParams = new HttpParams();
     queryParams = queryParams.append("category",category).append("title",title)
                    .append("author",author);
      return this.http.get(API_URL+'/search',{headers,params:queryParams});
  }

  getSubscribedBook(id: any) : Observable<any> { 
    return this.http.get(API_URL + '/readers/'+id+'/books',{headers});
  }

  subscribeBook(bookid:any, userId:any): Observable<any> { 
    return this.http.post(API_URL +"/"+bookid+'/subscribe', {
      bookId: bookid,
      userId: userId
    },{headers});
  }
    
  
  getAllSubscribedBooks(id: any) : Observable<any> { 
    return this.http.get(API_URL + '/readers/'+id+'/books',{headers});
  }

  cancelSubscription(subId:number, userId:any): Observable<any>  {
    return this.http.post(API_URL +"/readers/"+userId+"/books/"+subId+"/cancel-subscription",null,{headers});
    
  }

  // getAllSubscribedBooks(id : any) : Observable<any> {
  //   return this.http.get(API_URL + '/reader/'+id+'/books');
  // }

  
  // getBooksCreatedByAuthor(id : any): Observable<any> {
  //   return this.http.get(API_URL + '/author/'+id+'/getAllBooks');
  // }

  verifyIfLessThan24Hrs(bookId: any) : boolean{
    var currentTimestamp = Date.now();
    var twentyFourHours = 24 * 60 * 60 * 1000;
   
    let user = this.tokenStorageService.getUser();
    let subs =  user.subscriptions;
    for(let sub of subs){
      let subscriptionTimeStamp = Date.parse(sub.subscriptionTime);
      
      if(bookId === sub.bookId){
        if((currentTimestamp - subscriptionTimeStamp) > twentyFourHours){
          return false;
        }

      }
    }
    return true;
  }

}

