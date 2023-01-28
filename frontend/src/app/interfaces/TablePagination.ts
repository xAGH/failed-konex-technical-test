import { Medicine } from "src/app/medicine/interfaces/Medicine";
import { Sale } from "src/app/sale/interfaces/Sale";

export interface TablePagination {
    medicines: Medicine[] | Sale[],
    rowsPerPage: number[],
    totalElements: number,
    rows: number,
    currentPage: number
}
