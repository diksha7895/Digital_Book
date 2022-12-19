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
  isCreateBookFailed=false;
  isLoggedIn=false;
  createbook : any ={
    title : '',
    category : '',
    image : '',
    price : '',
    publisher : '',
    active : '',
    content : ''
  };
  constructor(private authorService : AuthorService) { }

  onCreate(){
    const{title,category,image,price,publisher,content} = this.createbook;
    this.authorService.createBook(this.createbook).subscribe(data=>{
      console.log(data.message);
      this.isSuccessful=true;
    },
    error=>{
      console.error(error);
      this.errorMessage = error.error;
      this.isSuccessful = false;
    })
  }

}
