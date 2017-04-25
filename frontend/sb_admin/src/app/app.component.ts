import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {JwtStorageService} from './shared/services/jwtStorage/jwt-storage.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    constructor(public router: Router, private jwtStorageService:JwtStorageService) { 
        jwtStorageService.init();
    }

    ngOnInit() {
        // this.router.navigate(['/login']);
    }
}
