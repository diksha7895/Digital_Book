import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})
export class CreatebookComponent implements OnInit {

  createbook : any ={
    title : '',
    category : '',
    image : '',
    price : '',
    publisher : '',
    active : '',
    content : ''
  };
  constructor() { }

  ngOnInit(): void {
  }

}
