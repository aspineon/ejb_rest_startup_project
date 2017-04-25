import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login/login.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    private loginService : LoginService;
    router:Router;

    constructor(loginService : LoginService, router:Router) { 
        this.loginService = loginService;
        this.router = router;
    }

    ngOnInit() {}

    toggleSidebar() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle('push-right');
    }
    rltAndLtr() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle('rtl');
    }

    logout() {
        this.loginService.logout().subscribe(
                    data => this.logoutDone(data), // put the data returned from the server in our variable
                    error => this.logoutFail(error), // in case of failure show this message
                    () => console.log("Job Done Get !")//run this code in all cases
                );;
    }

    logoutDone(data:any) {
        this.router.navigate(['login']); // go to login page!
    }
  
    logoutFail(error:Response) {
        //let message = error.json().message;
        //this.error = "Error: " + message;
        //this.showError = true;
    }
}
