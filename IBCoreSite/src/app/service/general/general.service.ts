import { environment } from './../../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';

@Injectable()
export class GeneralService {
	public environment = environment;

	public extractData(res) {
		let body = res.json();
		return body || [];
	}

	public createObservable(data: any): Observable<any> {
		return Observable.create((observer: Observer<any>) => {
			observer.next(data);
			observer.complete();
		});
	}

	public handleError(error: any) {
		// console.error(error);
		return Observable.throw(error.json() || 'Server error');
	}
}
