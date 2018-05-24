import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInstagramChannelComponent } from './add-instagram-channel.component';

describe('AddInstagramChannelComponent', () => {
  let component: AddInstagramChannelComponent;
  let fixture: ComponentFixture<AddInstagramChannelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddInstagramChannelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddInstagramChannelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
