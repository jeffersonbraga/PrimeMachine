import { Component, Injector, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GenericModalComponent } from 'core/componentes/generic-modal/generic-modal.component';
import { ControlService } from 'core/services/control/control.service';
import { UserService } from 'core/services/user/user.service';

@Component({
  selector: 'app-cancel-subscription',
  templateUrl: './cancel-subscription.component.html',
  styleUrls: ['./cancel-subscription.component.css']
})
export class CancelSubscriptionComponent implements OnInit {

    account: any;
  
    currentModal : GenericModalComponent;
    returnComponent: string;
  
    constructor(
      private userService: UserService, 
      private controlApp: ControlService,
      private injector: Injector,
      private router: Router) {
        this.currentModal = this.injector.get("currentModal");
    }
  
    ngOnInit() {
      this.account = this.controlApp.account;
      this.returnComponent = this.userService.returnComponent;
    }
  
    cancel() {
      this.currentModal.setDynamicModalContent(this.returnComponent);
    }
  
    cancelSubscription() {
      this.userService.cancelSubscription(this.account.account).subscribe(
        result => this.onCancelSubscription(result),
        error => console.log(error)
      );
    }
  
    onCancelSubscription(result) {
      this.router.navigateByUrl('https://woopsocial.com/home');
    }

}
