import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpsertMedicineComponent } from './upsert-medicine.component';

describe('UpsertMedicineComponent', () => {
  let component: UpsertMedicineComponent;
  let fixture: ComponentFixture<UpsertMedicineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UpsertMedicineComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UpsertMedicineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
