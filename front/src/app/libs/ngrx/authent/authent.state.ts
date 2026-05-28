import {AuthUser} from '../../models/auth.model';

export enum AuthentTypeState {
  logged,
  logout,
  expired,
}

export interface AuthentState {
  hasError: boolean
  state: AuthentTypeState | undefined
  user: AuthUser | undefined
}

export const AUTHENT_FEATURE_KEY = 'authent';
