import { NgModule, Optional, SkipSelf, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlService } from '../../services/control/control.service';
import { EnsureModuleLoadedOnceGuard } from './ensureModuleLoadedOnceGuard';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { JumbotronComponent } from '../jumbotron/jumbotron.component';
import { Quick_searchComponent } from '../quick_search/quick_search.component';
import { Quick_viewComponent } from '../quick_view/quick_view.component';
import { SidebarComponent } from '../sidebar/sidebar.component';

@NgModule({
  imports: [CommonModule],
  declarations: [],
  exports: [CommonModule],
  providers: [FooterComponent, HeaderComponent, JumbotronComponent, Quick_searchComponent, Quick_viewComponent, SidebarComponent], // these should be singleton
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})

export class CoreModule extends EnsureModuleLoadedOnceGuard {    //Ensure that CoreModule is only loaded into AppModule

  //Looks for the module in the parent injector to see if it's already been loaded (only want it loaded once)
  constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
    super(parentModule);
  }
}