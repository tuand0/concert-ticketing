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
    const storedRole = sessionStorage.getItem('role');
    const storedId = sessionStorage.getItem('userId');

    if (payload) {
      this._currentUser.set({
        id: storedId ? Number(storedId) : 0,
        email: payload.email ?? payload.sub ?? '',
        roles: payload.roles?.length
          ? payload.roles
          : storedRole
            ? [storedRole]
            : [],
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

  public login(email: string, password: string): Observable<void> {
    return this.http.post<AuthResponse>(`${this.baseApiUrl}/auth/login`, {email, password}).pipe(
      map((response) => {
        const payload = this.decodeJwt(response.token) as JWTPayload | null

        const roles = payload?.roles?.length
          ? payload.roles
          : [response.role];

        sessionStorage.setItem('token', response.token)
        sessionStorage.setItem('role', response.role);
        sessionStorage.setItem('userId', response.userId.toString());

        this._currentUser.set({
          id: response.userId,
          email,
          roles,
          token: response.token,
        });
      }),
    )
  }

  public logout(): void {
    sessionStorage.removeItem('token')
    sessionStorage.removeItem('role')
    sessionStorage.removeItem('userId');
    this._currentUser.set(null)
    this.router.navigate(['/']).then()
  }

  public isAdmin(): boolean {
    return this._currentUser()?.roles.includes('administrateur')
      ?? false
  }

  public isOrganisateur(): boolean {
      return this._currentUser()?.roles.includes('organisateur') ?? false;
  }

  public isClient(): boolean {
    return this._currentUser()?.roles.includes('client') ?? false;
  }

  public getCurrentUserId(): number | null {
    const user = this._currentUser();

    if (user) {
      return user.id;
    }

    const storedId = sessionStorage.getItem('userId');

    return storedId ? Number(storedId) : null;
  }
}
