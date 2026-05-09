import {CanActivateChildFn, Router} from '@angular/router'
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';

export const authenticatedGuard: CanActivateChildFn = () => {
  return inject(AuthService).isLoggedIn() || inject(Router).createUrlTree(['/login'])
}
