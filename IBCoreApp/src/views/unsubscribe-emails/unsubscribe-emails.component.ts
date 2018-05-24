import { Component, OnInit } from '@angular/core';
import { UserService } from 'core/services/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-unsubscribe-emails',
  templateUrl: './unsubscribe-emails.component.html',
  styleUrls: ['./unsubscribe-emails.component.css']
})
export class UnsubscribeEmailsComponent implements OnInit {

  sub: Subscription;
  loading = true;

  constructor(
    private UserService: UserService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(
      params => this.unsubscribeEmail(params['email'])
    );
  }

  unsubscribeEmail(email: string) {

    this.UserService.unsubscribeEmails(email).
    subscribe(
        response => this.mapResponse(response)
    );
  }

  mapResponse(response) {
    this.loading = false;
  }

}
