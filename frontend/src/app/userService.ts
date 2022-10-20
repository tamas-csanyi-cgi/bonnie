import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { BASE_PATH } from "generated-client";
import { Observable } from "rxjs";

@Injectable()
export class UserService {

    basePath: string;
    loggedIn: boolean = false;

    constructor(
        protected httpClient: HttpClient,
        @Inject(BASE_PATH) basePath: string) {

        this.basePath = basePath;

    }

    isLoggedIn() : boolean {
        return this.loggedIn;
    }

    setLoggedIn(val : boolean) {
        this.loggedIn = val;
    }

    public login(email: string, password: string) : Observable<Object> {
        const body = {
            username: email,
            password: password
        };
        return this.httpClient.request('post', `${this.basePath}/login`,
            {
                body: body,
                responseType: 'text'
            }
        );
    }

}