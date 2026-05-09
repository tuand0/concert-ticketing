import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { routes } from './app.routes';
import {bearerTokenInterceptor} from './libs/interceptors/bearer-token-interceptor';

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
    provideHttpClient()
  ]
};
