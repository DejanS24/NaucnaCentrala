import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    if (this.authenticationService.isLoggedIn()) {
      var allowed_roles = next.data["roles"] as Array<string>;
      var my_roles = this.authenticationService.getRoles();
      console.log(allowed_roles);
      console.log(my_roles);
    
      for(var i in my_roles){
        if(allowed_roles.includes(my_roles[i])){
          return true;
        }
      }

      this.router.navigate(['']);
      return false;
    }
    else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
