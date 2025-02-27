import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthStorageService {

  private readonly TOKEN_KEY = 'jwt';

  constructor() { }

  reset(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | undefined {
    const token = localStorage.getItem(this.TOKEN_KEY);
    return token ? token : undefined;
  }
}
