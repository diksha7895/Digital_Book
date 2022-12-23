import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorService } from '../_services/author.service';
import { BookstorageService } from '../_services/bookstorage.service';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {

  isSuccessful = false;
  errorMessage = "";
  book : any = {
    logo: null,
    title: null,
    publisher: null,
    category: null,
    content: null,
    price: null
  }

  constructor(private authorService : AuthorService, private bookStorage : BookstorageService,  private router: Router) { }

  ngOnInit(): void {
    this.book = this.bookStorage.getBook();
  }

  onUpdate(){
    this.book.publishedDate = null;
    this.authorService.updateBook(this.book).subscribe(
      ()=>{
        this.isSuccessful = true;
        setTimeout(() => {
          this.router.navigateByUrl('/allmybooks')
        }, 2000);
      },
      error => {
        console.error(error);
      }
    );
  }

}
