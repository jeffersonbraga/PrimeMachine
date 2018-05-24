import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsideHorizontalTextIconComponent } from './aside-horizontal-text-icon.component';

describe('AsideHorizontalTextIconComponent', () => {
  let component: AsideHorizontalTextIconComponent;
  let fixture: ComponentFixture<AsideHorizontalTextIconComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsideHorizontalTextIconComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsideHorizontalTextIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
