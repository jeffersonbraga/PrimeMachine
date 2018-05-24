import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicModalContentComponent } from './dynamic-modal-content.component';

describe('DynamicModalContentComponent', () => {
  let component: DynamicModalContentComponent;
  let fixture: ComponentFixture<DynamicModalContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicModalContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicModalContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
