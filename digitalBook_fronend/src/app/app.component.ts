import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';
//import { HomeComponent } from './home/home.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showReaderBoard = false;
  showAuthorBoard = false;
  username?: string;
 

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.username = user.username;
      
      this.showReaderBoard = this.roles.includes('READER');
      this.showAuthorBoard = this.roles.includes('AUTHOR');
    
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
