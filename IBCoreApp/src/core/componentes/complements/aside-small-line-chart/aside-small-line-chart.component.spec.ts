import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsideSmallLineChartComponent } from './aside-small-line-chart.component';

describe('AsideSmallLineChartComponent', () => {
  let component: AsideSmallLineChartComponent;
  let fixture: ComponentFixture<AsideSmallLineChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsideSmallLineChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsideSmallLineChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
