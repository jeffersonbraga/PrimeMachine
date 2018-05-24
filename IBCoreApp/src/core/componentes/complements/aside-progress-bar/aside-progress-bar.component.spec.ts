import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsideProgressBarComponent } from './aside-progress-bar.component';

describe('AsideProgressBarComponent', () => {
  let component: AsideProgressBarComponent;
  let fixture: ComponentFixture<AsideProgressBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsideProgressBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsideProgressBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
