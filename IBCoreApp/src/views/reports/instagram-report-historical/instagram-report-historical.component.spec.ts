import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstagramReportHistoricalComponent } from './instagram-report-historical.component';

describe('InstagramReportHistoricalComponent', () => {
  let component: InstagramReportHistoricalComponent;
  let fixture: ComponentFixture<InstagramReportHistoricalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstagramReportHistoricalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstagramReportHistoricalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
