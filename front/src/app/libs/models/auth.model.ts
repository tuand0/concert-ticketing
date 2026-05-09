export interface AuthResponse {
  token: string
  role: string
}

export interface AuthUser {
  email: string
  roles: string[]
  token: string
}
