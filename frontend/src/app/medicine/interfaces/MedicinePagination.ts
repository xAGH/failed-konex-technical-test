import { Pagination } from 'src/app/interfaces/Pagination';

export interface MedicinePagination extends Pagination {
  filters?: string[];
  values?: string[];
}
