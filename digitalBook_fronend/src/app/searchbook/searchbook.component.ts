import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { Router } from "@angular/router";
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
// export class SearchbookComponent implements OnInit    {
export class SearchbookComponent   {
  book : any = {
    category : '',
    title : '',
    author : ''
  };
  books : any[] = []
  searchbook : any = {
    logo : null,
    category : null,
    title : null,
    price : null,
    publisher : null,
    content : null
  }
  subs : any[] = []
  
 // isSubscribeFailed=false;
  isSearchSuccess: any;
  isSearchSuccessful=false;
  isSearchFailed=false;
  errorMessage='';
  showWarningMessage=false;
  warningMessage="";
  showSuccess=false;
  successMessage="";
  subscriptions='';
  //isLoggedIn = this.tokenStorage.getUser() !== null;
  isUserLoggedIn = this.tokenStorage.getUser() !== null;

  constructor(private userService: UserService, private tokenStorage : TokenStorageService) { }
  

  //  ngOnInit(): void {
  //   this.isLoggedIn = !!this.tokenStorage.getToken();

  //   if (this.isLoggedIn) {
  //     const user = this.tokenStorage.getUser();
  //     this.roles = user.roles;
  //     this.username = user.username;
      
  //     this.showReaderBoard = this.roles.includes('READER');
  //     this.showAuthorBoard = this.roles.includes('AUTHOR');
    
  //   }
  //  }

  

  onSearch(){
     const{category,title,author} = this.book;
     this.userService.search(category,title,author).subscribe(
       data => {
         this.books = [];
         console.log(data);
         this.isSearchSuccessful=true;
         this.isSearchFailed=false;
         for(let b of data){
           this.searchbook = b;
           this.books.push(this.searchbook);
         }
       },
       err => {
               console.error(err);
               this.isSearchFailed=true;
               this.isSearchFailed=false;
               if(err instanceof HttpErrorResponse){
                 console.error(err.error.message);
                 this.errorMessage = err.error.message;
               }
              }
     );

   }

   
  oncancelSearch(){
    this.isSearchSuccess = false;
  }

  onClick(book:any){
      if(!this.isUserLoggedIn){
       this.showWarningMessage= true;
       this.warningMessage="Please create your account"; 
     }
     setTimeout(() => {
       this.showWarningMessage=false;
       this.warningMessage="";
     }, 2500);
   }

   showSubscription(bookId: any){
    let subs : any[] = this.tokenStorage.getUser().subscriptions;
    if(subs!=null){
    for(let sub of subs){
      if(bookId === sub.bookId) {
        return false;
      }
    }
  }
    return true;
  }

  onSubscribe(bookId:any){
    let subs : any [] = []; 
    this.userService.subscribeBook(bookId, this.tokenStorage.getUser().id).subscribe(
      data=> {
        console.log(data);
        let user = this.tokenStorage.getUser();
       // let subs : any [] = []; 
        subs=user.subscriptions;
        console.log(user.subscriptions);
      /* subs.push({
          userId:user.id,
          bookId:bookId,
          id:data.id,
          subscriptionTime:data.subscriptionTime,
          active:true
        })*/
       // user.subscriptions = subs;
        console.log("subs" +subs);
        this.tokenStorage.saveUser(user);
        this.successMessage="Subscription successful!";
        this.showSuccess=true;
        console.log("success "+this.showSuccess);
        console.log("msg : "+this.successMessage);
        setTimeout(() => {
          this.showSuccess=false;
          this.successMessage="";
        }, 2500);
      },
      err => {
        console.error(err);
      }
    );
  }


  showUnSubscribe(bookId:any){
    let subs : any[] = this.tokenStorage.getUser().subscriptions;
    if(subs!=null){
      for(let sub of subs){
        if(bookId === sub.bookId && this.userService.verifyIfLessThan24Hrs(bookId)) {
          console.log(bookId === sub.bookId);
          return true;
        }
      }
  }
    return false; 
  }


  onUnSubscribe(bookId: any){
    let subs : any[] = this.tokenStorage.getUser().subscriptions;
    let subId:number;
    console.log("subs :"+this.tokenStorage.getUser().subscriptions);
    if(subs!=null){
    for(let sub of subs){
      if(bookId === sub.bookId) {
        subId = sub.id;
        console.log("subId :"+subId);
        this.cancelSubscription(subId);
      }
    }
  }
}


  cancelSubscription(subId:number){
    this.userService.cancelSubscription(subId, this.tokenStorage.getUser().id).subscribe(
      data=>{
        console.log(data);
        let user = this.tokenStorage.getUser();
        let subs = user.subscriptions;
        subs = subs.filter((sub: { id: number; }) => sub.id !== subId)
        user.subscriptions = subs;
        this.tokenStorage.saveUser(user);
        this.successMessage="Your book subscription cancelled successfully!";
        this.showSuccess=true;
        setTimeout(() => {
          this.showSuccess=false;
          this.successMessage="";
        }, 3000);
      },
      err=>{
        console.error(err);
      }
    );
  }

  
}
export interface Books{
  id:number;
  category:string;
  title:string;
  author:string;
  publisher:string;
  price:number;
  logo:string;
}
