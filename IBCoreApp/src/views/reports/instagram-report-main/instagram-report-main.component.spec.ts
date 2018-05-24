import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstagramReportMainComponent } from './instagram-report-main.component';

describe('InstagramReportMainComponent', () => {
  let component: InstagramReportMainComponent;
  let fixture: ComponentFixture<InstagramReportMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstagramReportMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstagramReportMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
