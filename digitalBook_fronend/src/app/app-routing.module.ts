import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { BookinfoComponent } from './bookinfo/bookinfo.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { AllmybooksComponent } from './allmybooks/allmybooks.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';



const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'searchbook', component: SearchbookComponent },
  { path: 'createbook', component: CreatebookComponent},
  { path: 'updatebook', component: UpdatebookComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'allmybooks', component: AllmybooksComponent},
  { path: 'bookinfo/:id', component: BookinfoComponent},
 
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
