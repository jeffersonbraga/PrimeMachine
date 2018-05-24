import { LaudarComponent } from './../views/laudar/laudar.component';
import { RealizarComponent } from './../views/realizar/realizar.component';
import { AgendarComponent } from './../views/agendar/agendar.component';
import { CadastrarComponent } from './../views/cadastrar/cadastrar.component';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateStreamViaAuthguard } from 'guards/can-activate-stream-via.authguard';
import { CanActivateViaAuthguard } from 'guards/can-activate-via.authguard';
import { CanAlwaysActivateViaAuthguard } from 'guards/can-always-activate-via.authguard';
import { ComposePostComponent } from 'views/compose-post/compose-post.component';
import { FeedComponent } from 'views/feed/feed.component';
import { HomeComponent } from 'views/home/home.component';
import { LoginComponent } from 'views/login/login.component';
import { NewUserComponent } from 'views/new-user/new-user.component';
import { ReportComponent } from 'views/reports/report/report.component';
import { RssfeedComponent } from 'views/rssfeed/rssfeed.component';
import { ScheduleComponent } from 'views/schedule/schedule.component';
import { UnsubscribeEmailsComponent } from 'views/unsubscribe-emails/unsubscribe-emails.component';

// import { LoginModule } from '../login/login.module';
const app_routes: Routes = [
    {
        path: 'home*',
        pathMatch: 'full',
        redirectTo: '/home'
    },
    {
        path: 'home',
        component: HomeComponent,
        data: {
            breadcrumb: 'Home'
        },
        children: [
            {
                path: 'schedule', component: ScheduleComponent,
                data: {
                    breadcrumb: 'Scheduled Posts'// "Agendamento"
                }
            },
            {
                path: 'compose', component: ComposePostComponent,
                data: {
                    breadcrumb: 'New Post'// "Novo Post"
                }
            },
            {
                path: 'cadastrar', component: CadastrarComponent,
                data: {
                    breadcrumb: 'Cadastrar'// "Novo Post"
                }
            },
            {
                path: 'agendar', component: AgendarComponent,
                data: {
                    breadcrumb: 'Agendar'// "Novo Post"
                }
            },
            {
                path: 'realizar', component: RealizarComponent,
                data: {
                    breadcrumb: 'Realizar'// "Novo Post"
                }
            },
            {
                path: 'laudar', component: LaudarComponent,
                data: {
                    breadcrumb: 'Laudo clínico'// "Novo Post"
                }
            },
            {
                path: 'compose/:id', component: ComposePostComponent,
                data: {
                    breadcrumb: 'Edit Post'// "Edição de Post"
                }
            },
            { path: 'compose/:url', component: ComposePostComponent,
                data: {
                    breadcrumb: 'New Post'// "Novo Post"
                } 
            },
            {
                path: 'report', component: ReportComponent,
                data: {
                    breadcrumb: 'Analytics'// "Meus Relatórios"
                }
            },
            {
                path: 'rssfeed', component: RssfeedComponent,
                data: {
                    breadcrumb: 'Rss Feeds'// "Meus Rss Feeds"
                }
            },
            {
                path: 'stream', component: FeedComponent,
                data: {
                    breadcrumb: 'Social Streams'// "Streams"
                }
            }
        ]
    },
    { path: 'login', component: LoginComponent },
    { path: 'matrix/:matrix', component: LoginComponent },
    { path: 'newuser/:url', component: NewUserComponent },
    { path: 'unsubscribeemail/:email', component: UnsubscribeEmailsComponent },
    { path: '**', redirectTo: '/home/stream' }
    // catch any unfound routes and redirect to home page
];

export const app_routing = RouterModule.forRoot(app_routes);