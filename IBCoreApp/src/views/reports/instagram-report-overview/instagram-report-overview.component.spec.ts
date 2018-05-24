import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstagramReportOverviewComponent } from './instagram-report-overview.component';

describe('InstagramReportOverviewComponent', () => {
  let component: InstagramReportOverviewComponent;
  let fixture: ComponentFixture<InstagramReportOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstagramReportOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstagramReportOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
