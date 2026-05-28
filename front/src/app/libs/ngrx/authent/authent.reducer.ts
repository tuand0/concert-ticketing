import {AuthentState, AuthentTypeState} from './authent.state';
import {createReducer, on} from '@ngrx/store';
import {authentAction} from './authent.action';

const initialAuthentState: AuthentState = {
  hasError: false,
  user: undefined,
  state: undefined
}

export const authentReducer = createReducer(
  initialAuthentState,
  on(authentAction.onAuthentSucceeded, (state, {token}) => ({
    ...state,
    hasError: false,
      token
  })),

on(authentAction.onAuthentFailed, (state) => ({
  ...state,
  hasError: true
}))
)
