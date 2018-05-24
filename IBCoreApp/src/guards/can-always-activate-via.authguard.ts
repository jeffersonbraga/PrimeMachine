import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { LoginService } from 'core/services/login/login.service';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CanAlwaysActivateViaAuthguard implements CanActivate {

  constructor(private router: Router, private permissions: LoginService) {}
  canActivate(): Observable<boolean>|Promise<boolean>|boolean {
    return true;
  }
}
