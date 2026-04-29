import {computed, inject, Injectable, signal} from '@angular/core'
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {AuthResponse, AuthUser} from '../models/auth.model';
import {Router} from '@angular/router';

interface JWTPayload {
  email?: string
  sub?: string
  roles?: string[]
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseApiUrl = 'http://localhost:4200/api'

  private readonly http = inject(HttpClient)
  private readonly router = inject(Router)

  private readonly _currentUser = signal<AuthUser | null>(null)
  readonly currentUser = this._currentUser.asReadonly()
  readonly isLoggedIn = computed(() => this._currentUser() !== null)

  constructor() {
    this.restoreSession()
  }

  private restoreSession(): void {
    const token = sessionStorage.getItem('token')
    if (!token) {
      return
    }
    const payload = this.decodeJwt(token) as JWTPayload | null
    if (payload) {
      this._currentUser.set({
        email: payload.email ?? payload.sub ?? '',
        roles: payload.roles ?? [],
        token,
      })
    }
  }

  private decodeJwt(token: string): Record<string, unknown> | null {
    try {
      const base64 = token.split('.')[1]
      return JSON.parse(atob(base64))
    } catch {
      return null
    }
  }

  login(email: string, password: string): Observable<void> {
    return this.http.post<AuthResponse>(`${this.baseApiUrl}/auth/login`, {email, password}).pipe(
      map((response) => {
        const payload = this.decodeJwt(response.token) as JWTPayload | null
        sessionStorage.setItem('token', response.token)
        this._currentUser.set({
          email,
          roles: payload?.roles ?? [],
          token: response.token,
        })
      }),
    )
  }

  logout(): void {
    sessionStorage.removeItem('token')
    this._currentUser.set(null)
    this.router.navigate(['/login']).then()
  }
}
