import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { LoginService } from 'core/services/login/login.service';
import { Observable } from 'rxjs/Observable';

declare const $;

@Injectable()
export class CanActivateViaAuthguard implements CanActivate {
/*
  constructor(private router: Router,
              private loginService: LoginService)
  {}

  canActivate(): boolean {
    if (!this.loginService.isLoggedIn()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }*/

  constructor(private router: Router, private permissions: LoginService) {}
  canActivate(): Observable<boolean>|Promise<boolean>|boolean {
    return this.permissions.isLoggedIn()
    .map(
      data => {
          setTimeout(() => {
            $('#sidebar').sidebar();
          }, 200);

          if (data === false) {
              this.router.navigate(['/login']);
              return data;
          }else {
              return data;
          }
      },
      error => {
          this.router.navigate(['/login']);
          return error;
      }
    );
    // if (this.permissions.isLogged == null) {
    //   return true;
    // } else if (this.permissions.isLogged) {
    //   return this.permissions.isLogged;
    // } else {
    //   this.router.navigateByUrl('https://woopsocial.com');
    // }
  }
}
