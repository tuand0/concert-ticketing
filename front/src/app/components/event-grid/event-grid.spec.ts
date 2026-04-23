import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventGrid } from './event-grid';

describe('EventGrid', () => {
  let component: EventGrid;
  let fixture: ComponentFixture<EventGrid>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventGrid]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventGrid);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
