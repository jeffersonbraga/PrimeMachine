import { KzMaskCurrencyDirective } from './directive/mask/kz-mask-currency.directive';
import { FormBuilder } from '@angular/forms';

// SERVICES
import { ContactService } from './service/contact/contact.service';
import { HomeService } from './service/home/home.service';
import { AccountService } from './service/account/account.service';

// MODULES
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgModule } from '@angular/core';

// COMPONENTS
import { CreateAccountComponent } from './component/create-account/create-account.component';
import { TestCreateAccountComponent } from './component/test-create-account/test-create-account.component';
import { LoginComponent } from './component/login/login.component';
import { AppComponent } from './app.component';
import { ContactComponent } from './component/contact/contact.component';
import { DynamicModalContentComponent } from './component/dynamic-modal-content/dynamic-modal-content.component';
import { FooterComponent } from './component/footer/footer.component';
import { GenericModalComponent } from './component/generic-modal/generic-modal.component';
import { HeaderSiteComponent } from './component/header-site/header-site.component';
import { HomeComponent } from './view/home/home.component';
import { PlansPricingComponent } from './component/plans-pricing/plans-pricing.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';

// ROUTER
import { app_routing } from './app.routing';
import { GeneralService } from 'app/service/general/general.service';
import { FacebookMessengerComponent } from './component/facebook-messenger/facebook-messenger.component';
import { TermsOfUseComponent } from 'app/component/terms-of-use/terms-of-use.component';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        GenericModalComponent,
        DynamicModalContentComponent,
        ContactComponent,
        FooterComponent,
        HeaderSiteComponent,
        PlansPricingComponent,
        CreateAccountComponent,
        TestCreateAccountComponent,
        LoginComponent,
        ForgotPasswordComponent,
        FacebookMessengerComponent,
        TermsOfUseComponent,
        KzMaskCurrencyDirective
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        app_routing,
        ModalModule.forRoot(),
        ReactiveFormsModule
    ],
    providers: [
        HomeService,
        ContactService,
        FormBuilder,
        AccountService,
        GeneralService
    ],
    bootstrap: [AppComponent],
})
export class AppModule { }
