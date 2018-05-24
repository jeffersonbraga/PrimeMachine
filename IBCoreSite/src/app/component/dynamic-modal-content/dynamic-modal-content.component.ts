import { ForgotPasswordComponent } from './../forgot-password/forgot-password.component';
import { LoginComponent } from './../login/login.component';
import { ContactComponent } from './../contact/contact.component';
import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, Input, ReflectiveInjector } from '@angular/core';

@Component({
	selector: 'dynamic-modal-content',
	templateUrl: './dynamic-modal-content.component.html',
	styleUrls: ['./dynamic-modal-content.component.css'],
	entryComponents: [
		ContactComponent,
		LoginComponent,
		ForgotPasswordComponent
	], // Reference to the components must be here in order to dynamically create them
})
export class DynamicModalContentComponent implements OnInit {

	currentComponent = null;
	@ViewChild('dynamicModalContentContainer', { read: ViewContainerRef }) dynamicComponentContainer: ViewContainerRef;

	constructor(private resolver: ComponentFactoryResolver) {

	}

	ngOnInit() {
	}

	// component: Class for the component you want to create
	// inputs: An object with key/value pairs mapped to input name/input value
	@Input() set componentData(data: {component: any, inputs: any }) {
		if (!data) {
			return;
		}

		// Inputs need to be in the following format to be resolved properly
		let inputProviders = Object.keys(data.inputs).map((inputName) => {return {provide: inputName, useValue: data.inputs[inputName]};});
		let resolvedInputs = ReflectiveInjector.resolve(inputProviders);
		
		// We create an injector out of the data we want to pass down and this components injector
		let injector = ReflectiveInjector.fromResolvedProviders(resolvedInputs, this.dynamicComponentContainer.parentInjector);
		
		// We create a factory out of the component we want to create
		let factory = this.resolver.resolveComponentFactory(data.component);
		
		// We create the component using the factory and the injector
		let component = factory.create(injector);
		
		// We insert the component into the dom container
		this.dynamicComponentContainer.insert(component.hostView);
		
		// We can destroy the old component is we like by calling destroy
		if (this.currentComponent) {
			this.currentComponent.destroy();
		}
		
		this.currentComponent = component;
	}
}
