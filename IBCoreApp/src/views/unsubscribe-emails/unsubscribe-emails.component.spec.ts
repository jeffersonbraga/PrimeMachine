import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnsubscribeEmailsComponent } from './unsubscribe-emails.component';

describe('UnsubscribeEmailsComponent', () => {
  let component: UnsubscribeEmailsComponent;
  let fixture: ComponentFixture<UnsubscribeEmailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnsubscribeEmailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnsubscribeEmailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
