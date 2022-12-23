import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {
    username: null,
    email: null,
    password: null,
    role: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  role='';

  constructor(private authService: AuthService) { }
   
  ngOnInit() : void{

  }


  onSubmit(): void {
    const { username, email, password, role } = this.form;
    //  let role = [];
    // role.push(roles);
   // let rolename:string=this.role.value==1?"reader":"author";
   //let roles:new Role(this.f['role'].value,)
    this.authService.register(username, email, password, role).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
