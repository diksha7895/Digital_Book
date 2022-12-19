import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
export class SearchbookComponent implements OnInit    {

  book : any = {
    title : '',
    category : '',
    author : '',
    publisher : ''

  };
  isSubscribeFailed=false;
  isSuccessful=false;
  isSearchFailed=false;
  errorMessage='';
  showReaderBoard=false;
  roles: string[] = [];
  content?:string;
  searchList?:Books[];
  isLoggedIn=false;
  showAuthorBoard=false;
  username?: string;
  subscribedId: number;

  constructor(private userService: UserService, private tokenStorage : TokenStorageService, private router : Router) { }
  

   ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorage.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      this.roles = user.roles;
      this.username = user.username;
      
      this.showReaderBoard = this.roles.includes('READER');
      this.showAuthorBoard = this.roles.includes('AUTHOR');
    
    }
   }

   searchBook() : void{
     this.userService.getSearchBookResult(this.book).subscribe(
       data => {
         this.isSuccessful=true;
         this.searchList=JSON.parse(data).searchList;
         if(this.searchList?.length==0){
          this.isSearchFailed=true;
          this.errorMessage='No Result Found';
          this.isSuccessful=false;

         }
       },
       err => {
        this.isSearchFailed=true;
        this.content = JSON.parse(err.error).message;
       }
     )
   }

   subscribeBook(book_id : number):void{
     this.subscribedId=book_id;
     this.userService.subscribeBook(this.username,book_id).subscribe(
       data => {
         console.log(data);
         this.isSubscribeFailed=false;
         this.router.navigate(['/subscribedBooks']);
       },
       err => {
         this.isSubscribeFailed=true;
         this.errorMessage=err.error.errorMessage;
         console.log(this.errorMessage);
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
