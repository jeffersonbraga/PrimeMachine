import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFacebookChannellsComponent } from './add-facebook-channells.component';

describe('AddFacebookChannellsComponent', () => {
  let component: AddFacebookChannellsComponent;
  let fixture: ComponentFixture<AddFacebookChannellsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddFacebookChannellsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFacebookChannellsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
