import { Pagination } from 'src/app/interfaces/Pagination';

export interface SalePagination extends Pagination {
  filterStartDate: Date;
  filterEndDate: Date;
}
