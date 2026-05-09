import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageConcerts } from './manage-concerts';

describe('ManageConcerts', () => {
  let component: ManageConcerts;
  let fixture: ComponentFixture<ManageConcerts>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageConcerts]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageConcerts);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
