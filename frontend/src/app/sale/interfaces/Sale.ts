export interface Sale {
  id?: number;
  saleDateTime: number;
  quantity: number;
  unitPrice?: number;
  totalPrice?: number;
  medicineId: number;
  medicineName?: string;
  createdAt?: Date;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
}
