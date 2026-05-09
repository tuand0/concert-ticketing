import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const organisateurGuard: CanActivateFn = () => {
  const router = inject(Router);
  const role = localStorage.getItem('role');

  if (role === 'organisateur') {
    return true;
  }

  return router.createUrlTree(['/login']);
};
