import { Component, OnInit, Inject } from '@angular/core';
import { LoginService } from '../shared/services/index';
import {Response} from '@angular/http';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    username:string;
    password:string;
    loginService:LoginService;
    showError:Boolean = false;
    error:String = "";
    router:Router;

    constructor(loginService : LoginService,  router : Router) { 
        this.loginService = loginService;
        this.router = router;
    }

    login() {
        this.resetErrorStatus();
        this.loginService.login(this.username,this.password).subscribe(
                    data => this.loginDone(data), // put the data returned from the server in our variable
                    error => this.loginFail(error), // in case of failure show this message
                    () => console.log("Job Done Get !")//run this code in all cases
                );
    }
  
  private resetErrorStatus() {
    this.showError = false;
    this.error = "";
  }
  
  loginDone(data:any) {
    this.router.navigate(['dashboard']); // go to dashboard/home
  }
  
  loginFail(error:Response) {
    let message = error.json().message;
    this.error = "Error: " + message;
    this.showError = true;
  }

    ngOnInit() {
    }

}
