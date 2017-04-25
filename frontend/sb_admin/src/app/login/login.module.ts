import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import {LoginService} from '../shared/services/login/login.service';
import {UrlConfig} from '../shared/urlconfig/url.config';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    LoginRoutingModule,
    NgbModule.forRoot(),
    FormsModule
  ],
  declarations: [LoginComponent],
  providers: []
})
export class LoginModule { }
