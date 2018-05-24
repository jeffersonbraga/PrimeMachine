import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInstagramSchedulingComponent } from './add-instagram-scheduling.component';

describe('AddInstagramSchedulingComponent', () => {
  let component: AddInstagramSchedulingComponent;
  let fixture: ComponentFixture<AddInstagramSchedulingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddInstagramSchedulingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddInstagramSchedulingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
