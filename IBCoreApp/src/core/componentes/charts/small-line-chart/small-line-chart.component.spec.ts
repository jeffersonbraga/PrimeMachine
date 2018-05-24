import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallLineChartComponent } from './small-line-chart.component';

describe('SmallLineChartComponent', () => {
  let component: SmallLineChartComponent;
  let fixture: ComponentFixture<SmallLineChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmallLineChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmallLineChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
