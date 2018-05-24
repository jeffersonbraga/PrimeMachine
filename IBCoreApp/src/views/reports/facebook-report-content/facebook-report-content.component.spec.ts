import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacebookReportContentComponent } from './facebook-report-content.component';

describe('FacebookReportContentComponent', () => {
  let component: FacebookReportContentComponent;
  let fixture: ComponentFixture<FacebookReportContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacebookReportContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacebookReportContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
