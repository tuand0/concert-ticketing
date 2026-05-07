import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http'
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {catchError, throwError} from 'rxjs';

export const bearerTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService)
  const token = authService.currentUser()?.token
  if (token) {
    req = req.clone({
      setHeaders: {Authorization: `Bearer ${token}`}
    });
  }

  return next(req).pipe(
    catchError((err) => {
      if (err instanceof HttpErrorResponse && err.status === 401) {
        authService.logout()
      }
      return throwError(() => err)
    })
  )
}
