import { Inject, EventEmitter } from '@angular/core';
import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http';
import {Headers, RequestOptions} from '@angular/http';
import {UrlConfig} from '../../urlconfig/url.config';
import { Observable } from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/throw'; 
import {LoggedUser} from './loggedUser';
import 'rxjs/Rx';
import { AuthHttp } from 'angular2-jwt';

@Injectable()
export class LoginService {
    
    authHttp : AuthHttp;
    http : Http;
    urlConfig : UrlConfig;
    loggedUser : LoggedUser;
    
    // Observable auth token source
    private authTokenSource : BehaviorSubject<string>;
    // Observable navItem stream
    authTokenItem : Observable<string>;

    private authTokenDeletionSource : BehaviorSubject<number>;
    authTokenDeletionItem : Observable<number>;
    
    constructor(http:Http, urlConfig:UrlConfig, authHttp:AuthHttp) {
      this.http = http;
      this.urlConfig = urlConfig;
      this.loggedUser = null;
      this.authTokenSource = new BehaviorSubject<string>("");
      this.authTokenItem =  this.authTokenSource.asObservable();
      this.authTokenDeletionSource = new BehaviorSubject<number>(0);
      this.authTokenDeletionItem =  this.authTokenDeletionSource.asObservable();
      this.authHttp = authHttp;
    }  

    isAlreadyLoggedIn() {

    }

    tokenHasBeenReleased(token:string) {
      this.authTokenSource.next(token);
    }

    tokenMustBeDeleted() {
      this.authTokenDeletionSource.next(1);
    }
    
    login(username:string, password:string) {
        let body = JSON.stringify({"username":username,"password":password});
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers, method: "post" });
        return new Observable((observer:any) => {
          this.performLogin(body,options)
             .subscribe(
                data => this.loginDone(data, observer), // put the data returned from the server in our variable
                error => this.loginFail(error, observer), // in case of failure show this message
                () => console.log("Login done!")//run this code in all cases
             );
        });
    }

    logout() {
      return new Observable((observer:any) => {
          this.performLogout()
             .subscribe(
                data => this.logoutDone(data, observer), // put the data returned from the server in our variable
                error => this.loginFail(error, observer), // in case of failure show this message
                () => console.log("Logout done !")//run this code in all cases
             );
        });
    }
  
    private loginDone(data:any, observer:any) {
      this.loggedUser = LoggedUser.fromJson(data);
      observer.next(data);
      observer.complete();
      this.tokenHasBeenReleased(this.loggedUser.accessToken);
    }
  
    private loginFail(error:Response, observer:any) {
      observer.error(error)
    } 

    private logoutDone(data:any, observer:any) {
      this.loggedUser = null;
      observer.next(data);
      observer.complete();
      this.tokenMustBeDeleted();
    }
  
    private logoutFail(error:Response, observer:any) {
      observer.error(error)
    } 

    private performLogout() {
      return this.authHttp.get(this.urlConfig.logoutUrl).catch(this.handleLogoutError);
    }
  
    private performLogin(body:string,options:RequestOptions) {
      return this.http.post(this.urlConfig.loginUrl, body,options)  
            .map(res => res.json())
            .catch(this.handleLoginError);
    }

    private handleLoginError (error: Response) {
        console.error('Login Error:' + error);
        return Observable.throw(error);
    }

    private handleLogoutError (error: Response) {
        console.error('Logout Error: ' + error);
        return Observable.throw(error);
    }
}