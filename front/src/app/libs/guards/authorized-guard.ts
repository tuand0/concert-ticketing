import {CanActivateChildFn, Router} from '@angular/router'
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';

export const authorizedGuard: CanActivateChildFn = (route) => {
  const allowedRoles = route.data['roles'] ?? []
  const userRoles = inject(AuthService).currentUser()?.roles ?? []
  const isAllowed = allowedRoles.length === 0 || userRoles.some((role) => allowedRoles.includes(role))
  return isAllowed || inject(Router).createUrlTree(['/not-allowed'])
}
