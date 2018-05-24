import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsidePieChartDoubleComponent } from './aside-pie-chart-double.component';

describe('AsidePieChartDoubleComponent', () => {
  let component: AsidePieChartDoubleComponent;
  let fixture: ComponentFixture<AsidePieChartDoubleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsidePieChartDoubleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsidePieChartDoubleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
