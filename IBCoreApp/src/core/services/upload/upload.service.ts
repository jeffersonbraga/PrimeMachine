import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UploadService {
	progress: any;
	progressObserver: any;
	constructor(private http: Http) {
		/*this.progress = Observable.create(observer => {
			this.progressObserver = observer
		}).share();*/
	}

	public makeFileRequest (url: string, params: string[], files: File[]) : Observable<Object> {
		return Observable.create(observer => {
			let formData: FormData = new FormData(),
				xhr: XMLHttpRequest = new XMLHttpRequest();

			for (let i = 0; i < files.length; i++) {
				formData.append("uploads[]", files[i], files[i].name);
			}

			xhr.onreadystatechange = () => {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						observer.next(JSON.parse(xhr.response));
						observer.complete();
					} else {
						observer.error(xhr.response);
					}
				}
			};

			xhr.upload.onprogress = (event) => {
				this.progress = Math.round(event.loaded / event.total * 100);

				this.progressObserver.next(this.progress);
			};

			xhr.open('POST', url, true);
			xhr.send(formData);
		});
	}

	public makeSingleFileRequest (url: string, file: File) : Observable<Object> {
		return Observable.create(observer => {
			let formData: FormData = new FormData(),
				xhr: XMLHttpRequest = new XMLHttpRequest();

			formData.append("file", file, file.name);

			xhr.onreadystatechange = () => {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						observer.next(JSON.parse(xhr.response));
						observer.complete();
					} else {
						observer.error(xhr.response);
					}
				}
			};

			xhr.open('POST', url, true);
			xhr.send(formData);
		});
	}
}