import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacebookReportHistoricalComponent } from './facebook-report-historical.component';

describe('FacebookReportHistoricalComponent', () => {
  let component: FacebookReportHistoricalComponent;
  let fixture: ComponentFixture<FacebookReportHistoricalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacebookReportHistoricalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacebookReportHistoricalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
