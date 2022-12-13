import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

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
  searchList?:Book[];
  isLoggedIn=false;
  showAuthorBoard=false;
  username?: string;

  constructor(private userService: UserService, private tokenStorage : TokenStorageService) { }
  

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


  
}
