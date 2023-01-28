import { Medicine } from "src/app/medicine/interfaces/Medicine";
import { Sale } from "src/app/sale/interfaces/Sale";

export interface APIResponse<T> {
    status:  boolean;
    message: string;
    data:    T;
}

export interface ApiData {
    content:          Medicine[] | Sale[];
    pageable:         Pageable;
    totalElements:    number;
    totalPages:       number;
    last:             boolean;
    size:             number;
    number:           number;
    sort:             Sort;
    numberOfElements: number;
    first:            boolean;
    empty:            boolean;
}

export interface Pageable {
    sort:       Sort;
    offset:     number;
    pageSize:   number;
    pageNumber: number;
    paged:      boolean;
    unpaged:    boolean;
}

export interface Sort {
    empty:    boolean;
    unsorted: boolean;
    sorted:   boolean;
}
