import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';


import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { AllmybooksComponent } from './allmybooks/allmybooks.component';
import { BookinfoComponent } from './bookinfo/bookinfo.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { BoardUserComponent } from './board-user/board-user.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardUserComponent,
    SearchbookComponent,
    CreatebookComponent,
    AllmybooksComponent,
    BookinfoComponent,
    UpdatebookComponent
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
