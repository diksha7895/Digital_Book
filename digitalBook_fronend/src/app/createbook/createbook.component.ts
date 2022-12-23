import { Component } from '@angular/core';
import { AuthorService } from '../_services/author.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})
export class CreatebookComponent {

  isSuccessful = false;
  errorMessage = "";
  logo=false;
  
  isLoggedIn=false;
  createbook : any ={
    title : null,
    category : null,
    price : null,
    publisher : null,
    content : null,
    logo : null
  };
  constructor(private authorService : AuthorService) { }

  showLogo(){
    if(this.logo === true)
      this.logo = false;
    else
      this.logo=true;
  }

  onCreate(){
    this.authorService.createBook(this.createbook).subscribe(data=> {
      this.isSuccessful = true;
      setTimeout(() => {
        window.location.reload();
      }, 4000);
    },
    error=> {
      console.error(error);
      this.errorMessage = error.error;
      this.isSuccessful = false;
    })
  }
}
