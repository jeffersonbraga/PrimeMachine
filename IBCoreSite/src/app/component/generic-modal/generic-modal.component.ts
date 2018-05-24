import { ForgotPasswordComponent } from './../forgot-password/forgot-password.component';
import { LoginComponent } from './../login/login.component';
import { ContactComponent } from './../contact/contact.component';
import { Component, OnInit, ElementRef, ViewChild, Input, NgModule } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
	selector: 'generic-modal',
	templateUrl: './generic-modal.component.html',
	styleUrls: ['./generic-modal.component.css'],
})
export class GenericModalComponent implements OnInit {
	
	@ViewChild('genericModal') public modal:ModalDirective;

	@Input('headerTitle') headerTitle: string;
	@Input('componentClassName') componentClassName: string;
	@Input('data') data: string;

	@Input()
	currentModal : string; //GenericModalComponent;

	@Input() componentData : any;

	components: Object = {
		ContactComponent: ContactComponent,
		LoginComponent: LoginComponent,
		ForgotPasswordComponent: ForgotPasswordComponent
	}

	constructor() { 
	}

	ngOnInit() {
		this.setDynamicModalContent(this.componentClassName);
	}

	showModal():void {
		this.modal.show();
	}

	hideModal():void {
		this.modal.hide();
	}

	setDynamicModalContent(componentClassName : string):void {

		this.componentData = {
			component: this.components[componentClassName],
			inputs: {
				currentModal: this.currentModal
			}
		};
	}
}