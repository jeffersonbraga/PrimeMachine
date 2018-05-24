// import { LandingThreeComponent } from './component/landing-three/landing-three.component';
// import { LandingTwoComponent } from './component/landing-two/landing-two.component';
import { TestCreateAccountComponent } from './component/test-create-account/test-create-account.component';
import { TermsOfUseComponent } from './component/terms-of-use/terms-of-use.component';
import { CreateAccountComponent } from './component/create-account/create-account.component';
import { PlansPricingComponent } from './component/plans-pricing/plans-pricing.component';
import { HomeComponent } from './view/home/home.component';
import { RouterModule, Routes } from '@angular/router';

const app_routes: Routes = [
    {
        path: '*',
        pathMatch: 'full',
        redirectTo: '/home'
    },
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'plans',
        component: PlansPricingComponent
    },
    {
        path: 'create',
        component: CreateAccountComponent
    },
    {
        path: 'test-create',
        component: TestCreateAccountComponent
    },
    {
        path: 'terms',
        component: TermsOfUseComponent
    },
    // {
    //     path: 'new-user-fb',
    //     component: LandingTwoComponent
    // },
    // {
    //     path: 'new-user-ig',
    //     component: LandingThreeComponent
    // },
    { path: '**', pathMatch: 'full', redirectTo: '' }
];

export const app_routing = RouterModule.forRoot(app_routes);
