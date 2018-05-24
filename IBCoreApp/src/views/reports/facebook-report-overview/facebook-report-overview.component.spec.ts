import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacebookReportOverviewComponent } from './facebook-report-overview.component';

describe('FacebookReportOverviewComponent', () => {
  let component: FacebookReportOverviewComponent;
  let fixture: ComponentFixture<FacebookReportOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacebookReportOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacebookReportOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
