export interface Medicine {
  id: number;
  name: string;
  factoryLaboratory: string;
  manufacturingDate: Date;
  dueDate: Date;
  stock: number;
  unitPrice: number;
  createdAt: Date;
  updatedAt: Date | null;
  deletedAt: Date | null;
}
