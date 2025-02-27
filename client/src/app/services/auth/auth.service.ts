import {Injectable, OnDestroy, OnInit} from "@angular/core";
import {AuthStorageService} from "./auth-storage.service";
import {LoginReq} from "../../models/auth/login.model";
import {AuthRepository} from "../../repositories/auth.repository";
import {JwtResponse} from "../../models/auth/jwt.response";
import {RegisterReq} from "../../models/auth/register.model";
import {ToastService} from "../toast.service";
import {Role} from "../../models/auth/role.model";
import {jwtDecode} from "jwt-decode";
import {Utils} from "../utils";
import {BehaviorSubject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    isAuthorized$ = new BehaviorSubject<boolean>(false);

    constructor(
        private authStorage: AuthStorageService,
        private authRepo: AuthRepository,
        private toastService: ToastService,
    ) {
        this.updateAuthState();
        setInterval(() => this.updateAuthState(), 60000);
    }

    isAuthorized(): boolean {
        const token = this.authStorage.getToken();
        if (Utils.isUndefined(token)) {
            return false;
        }

        try {
            const payload: any = jwtDecode(token!);
            const now = Math.floor(Date.now() / 1000);
            if (Utils.isUndefined(payload.exp) ||
                Utils.isUndefined(payload.sub) ||
                Utils.isUndefined(payload.role)
            ) {
                console.log("Invalid JWT token");
                return false;
            }
            return payload.exp! > now;
        } catch (error) {
            console.error(error);
            return false;
        }
    }

    getRole(): Role | undefined {
        // DON'T remove this check before next unsafe logic
        if (!this.isAuthorized()) {
            return undefined;
        }

        const payload: any = jwtDecode(this.authStorage.getToken()!);
        return payload.role;
    }

    getLogin(): string | undefined {
        // DON'T remove this check before next unsafe logic
        if (!this.isAuthorized()) {
            return undefined;
        }

        const payload: any = jwtDecode(this.authStorage.getToken()!);
        return payload.sub;
    }

    logout(): void {
        this.authStorage.reset();
        this.updateAuthState();
    }

    login(req: LoginReq): void {
        this.authRepo.login(req).subscribe({
            next: (resp: JwtResponse) => {
                this.authStorage.saveToken(resp.token);
                this.updateAuthState();
            },
            error: () => this.logout()
        });
    }

    register(req: RegisterReq): void {
        this.authRepo.register(req).subscribe({
            next: (resp: JwtResponse) => {
                this.authStorage.saveToken(resp.token)
                this.updateAuthState();
            },
            error: () => this.logout()
        });
    }

    registerOther(req: RegisterReq): void {
        this.authRepo.registerOther(req).subscribe({
            next: () => this.toastService.success("User was successfully registered!"),
            error: () => this.toastService.error("User was not registered(")
        });
    }

    private updateAuthState(): void {
        const authState = this.isAuthorized();
        if (this.isAuthorized$.value != authState) {
            this.isAuthorized$.next(authState);
        }
    }
}
