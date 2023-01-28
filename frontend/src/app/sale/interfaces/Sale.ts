export interface Sale {
  id?: number;
  saleDateTime: number;
  quantity: number;
  unitPrice?: number;
  totalPrice?: number;
  medicineId: number;
  createdAt?: Date;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
}
