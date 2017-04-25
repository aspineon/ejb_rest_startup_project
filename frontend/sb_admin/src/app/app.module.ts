import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {JwtStorageService} from './shared/services/jwtStorage/jwt-storage.service';
import { LoginService } from './shared/services/login/login.service';
import {UrlConfig} from './shared/urlconfig/url.config';
import {AuthguardService} from './shared/services/authguard/authguard.service';
import {AuthService} from './shared/services/auth/auth.service';
import { AuthHttp, AuthConfig, AUTH_PROVIDERS, provideAuth } from 'angular2-jwt';



@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        AppRoutingModule
    ],
    providers: [JwtStorageService,
                LoginService,
                UrlConfig, 
                AuthguardService, 
                AuthService, 
                AuthHttp,
                provideAuth({
                    headerName: 'Authorization',
                    headerPrefix: 'Bearer',
                    tokenName: 'token',
                    tokenGetter: (() => localStorage.getItem('id_token')),
                    globalHeaders: [{ 'Content-Type': 'application/json' }],
                    noJwtError: true
                })],
    bootstrap: [AppComponent]
})
export class AppModule { }
