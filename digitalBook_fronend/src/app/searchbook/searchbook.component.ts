import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
export class SearchbookComponent implements OnInit    {

  book : any = {
    title : '',
    author : '',
    publisher : '',
    date : ''

  };

   constructor() { }

   ngOnInit(): void {
   }

  
}
