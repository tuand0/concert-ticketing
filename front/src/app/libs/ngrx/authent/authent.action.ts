import { createAction, props } from '@ngrx/store';

export const authentAction = {
  onLogin: createAction(
    '[Authent] User Login',
    props<{ username: string, password: string }>()
  ),

  onAuthentSucceeded: createAction(
    '[Authent] Authent Succeeded',
    props<{ token:string }>()
  ),

  onAuthentFailed: createAction(
    '[Authent] Authent failed'
  ),
}
