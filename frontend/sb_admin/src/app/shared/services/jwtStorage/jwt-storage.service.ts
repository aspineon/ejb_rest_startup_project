import { Injectable } from '@angular/core';
import { LoginService } from '../login/login.service';

@Injectable()
export class JwtStorageService {

  private loginService : LoginService
  private tokenId : string = 'id_token';

  constructor(loginService:LoginService) { 
    this.loginService = loginService
  }

  init() {
    this.loginService.authTokenItem.subscribe(token => {
      this.storeToken(token);
    });

    this.loginService.authTokenDeletionItem.subscribe(del => {
      var performOperation = (del == 0) ? false : true;
      this.deleteCurrentToken(performOperation);
    });
  }

  storeToken(token:string) {
    if ((token) && (token != ''))
      localStorage.setItem(this.tokenId, token);
  }

  deleteCurrentToken(performOperation:boolean) {
    if(performOperation)
      localStorage.removeItem(this.tokenId);
  }

}
