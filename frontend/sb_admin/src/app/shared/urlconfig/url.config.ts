export class UrlConfig {
  baseUrl : string = 'http://localhost:8080/limca_pvt/rest';
  loginUrl : string = this.baseUrl + '/login/in';
  logoutUrl : string = this.baseUrl + '/login/out';
}