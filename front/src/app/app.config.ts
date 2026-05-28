import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { routes } from './app.routes';
import {bearerTokenInterceptor} from './libs/interceptors/bearer-token-interceptor';
import {provideState, provideStore} from '@ngrx/store';
import {concertsFeatureKey} from './libs/ngrx/concerts/concerts.state';
import {concertsReducer} from './libs/ngrx/concerts/concerts.reducer';
import { provideEffects } from '@ngrx/effects';
import {allConcert$} from './libs/ngrx/concerts/concerts.effect';
import {provideStoreDevtools} from '@ngrx/store-devtools';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({
        eventCoalescing: true
    }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([bearerTokenInterceptor])),
    providePrimeNG({
        theme: {
            preset: Aura
        }
    }),
    provideHttpClient(),
    provideStore(),
    provideState({ name: concertsFeatureKey, reducer: concertsReducer }),
    provideEffects({
      allConcert$
    }),
    provideStoreDevtools()
]
};
