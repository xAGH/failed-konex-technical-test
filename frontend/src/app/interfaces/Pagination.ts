export interface Pagination {
  page: number;
  offset: number;
  sortBy?: string;
  filters?: string[];
  values?: string[];
}
