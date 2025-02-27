import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable} from 'rxjs';
import {AuthStorageService} from '../services/auth/auth-storage.service';
import {ToastService} from '../services/toast.service';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private authStorage: AuthStorageService,
    private toast: ToastService,
    private router: Router,
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authStorage.getToken();
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        switch (error.status) {
          case 0: {
            this.toast.error('X0', 'No connection with server')
            break;
          }
          case 401: {
            this.authStorage.reset();
            this.toast.warn('Oops', 'Looks like you are unauthorized. Try to sign in');
            this.router.navigate(['auth'])
            break;
          }
          case 403: {
            this.toast.warn('Oops', 'Looks like you don\'t have permissions for this resource');
            break;
          }
          default: this.toast.httpError(error);
        }

        throw error;
      })
    );
  }
}
