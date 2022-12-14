import { Component, OnInit } from '@angular/core';
import { BookstorageService } from '../_services/bookstorage.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content?: string;
  isSuccessful=false;
  successMessage="";
  activeSubscriptions=false;
  noActiveSubscriptions = false;


  user : any = {
    id: null,
    username: null,
    email: null,
    role: null,
    subscription: null
  };

  public books:any[] = []

  public book : any = {
    id: null,
    logo: null,
    title: null,
    authorId: null,
    authorName: null,
    publisher: null,
    category: null,
    content: null,
    price: null,
    publishedDate: null,
    active: null
  }

  constructor(private userService: UserService, private  tokenStorage : TokenStorageService,
    private bookStorage : BookstorageService) { }

    ngOnInit(): void {
      this.getAllBooks();
  
    }
  
    getAllBooks(){
      this.books = [];
      this.user = this.tokenStorage.getUser();
      this.userService.getAllSubscribedBooks(this.user.id).subscribe(
        data => {
          for(let b of data){
            this.book = b;
            if(this.book.publishedDate != null){
              let date = new Date(Date.parse(this.book.publishedDate));
              this.book.publishedDate = date.getDate()+'-'+(date.getMonth()+1)+'-'+date.getFullYear();
            } else {
              this.book.publishedDate = 'Not Available';
            }
            this.books.push(this.book);
          }
          if(this.books.length===0){
           this.noActiveSubscriptions = true;
           this.activeSubscriptions = false;
          }
          else {
            this.activeSubscriptions = true;
            this.noActiveSubscriptions = false;
          }
        },
        error => {
          console.error(error);
        }
      );
    }
  
    onClick(book : any) : void {
      this.bookStorage.setBook(book);
    }
  
    onCancelSubscription(bookId : any) : void {
      let subs : any[] = this.tokenStorage.getUser().subscription;
      let subId:number;
      for(let sub of subs){
        if(bookId === sub.bookId) {
          subId = sub.id;
          this.cancelSubscription(subId);
        }
      }
      
    }
  
    cancelSubscription(subId:number){
      this.userService.cancelSubscription(subId, this.tokenStorage.getUser().id).subscribe(
        data=>{
          console.log(data);
          let user = this.tokenStorage.getUser();
          let subs = user.subscription;
          subs = subs.filter((sub: { id: number; }) => sub.id !== subId)
          user.subscription = subs;
          this.tokenStorage.saveUser(user);
          this.successMessage="Subscription cancelled successfully!";
          this.isSuccessful=true;
          setTimeout(() => {
            this.isSuccessful=false;
            this.successMessage="";
          }, 2500);
          this.getAllBooks();
        },
        error=>{
          console.error(error);
        }
      );
    }
  
    verifyIfLessThan24Hrs(bookId: any){
      return this.userService.verifyIfLessThan24Hrs(bookId);
    }
  
}
