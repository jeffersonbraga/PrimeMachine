import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsideTextGrowthComponent } from './aside-text-growth.component';

describe('AsideTextGrowthComponent', () => {
  let component: AsideTextGrowthComponent;
  let fixture: ComponentFixture<AsideTextGrowthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsideTextGrowthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsideTextGrowthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
