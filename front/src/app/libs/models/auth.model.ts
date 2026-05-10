export interface AuthResponse {
  token: string
  role: string;
  userId: number;
}

export interface AuthUser {
  id: number
  email: string
  roles: string[]
  token: string
}
