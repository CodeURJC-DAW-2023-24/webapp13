import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileConsultComponent } from './profile-consult.component';

describe('ProfileConsultComponent', () => {
  let component: ProfileConsultComponent;
  let fixture: ComponentFixture<ProfileConsultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileConsultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileConsultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
