
export class LoggedUser {
  
  private id : string;
  public username : string;
  public name : string;
  public surname : string;
  public email : string;
  public accessToken : string;

  constructor() {}
  
  public static fromJson(json:any): LoggedUser {
    var user = new LoggedUser();
    user.id = json.id;
    user.username = json.username;
    user.name = json.name;
    user.surname = json.surname;
    user.email = json.email;
    user.accessToken = json.accessToken;
    return user;
  }
  
}