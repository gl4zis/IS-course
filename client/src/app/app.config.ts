import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {ConfirmationService, MessageService} from 'primeng/api';
import {provideAnimations} from '@angular/platform-browser/animations';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {providePrimeNG} from 'primeng/config';
import Indigo from '@primeng/themes/material';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    MessageService,
    ConfirmationService,
    provideAnimations(),
    providePrimeNG({
      theme: {
        preset: Indigo,
        options: {
          prefix: 'md',
          darkModeSelector: '.light-theme'
        }
      },
      ripple: true
    }),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
};
