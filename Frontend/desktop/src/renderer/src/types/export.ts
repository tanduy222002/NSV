import { Partner } from './partner';

export enum TicketStatus {
    Approved = 'APPROVED',
    Pending = 'PENDING',
    Rejected = 'REJECTED',
    All = 'ALL'
}

export enum ExportFormStep {
    First,
    Second
}

type Debt = {
    name: string;
    value: number;
    description: string;
    unit: string;
    due_date: string;
};

export type ImportBinWithSlot = {
    location: string;
    bin: {
        id: number;
        product: string;
        weight: number;
        packaged: string;
        product_img: string;
        quality_id: number;
        quality_with_type: string;
    };
    slot_id: number;
    slot_name: string;
    warehouse_name: string;
    in_slot_weight: number;
    taken_weight: number;
    taken_area: number;
};

export type ExportBin = {
    weight: number;
    price: number;
    note: string;
    quality_id: number;
    quality_detail?: any;
    package_type: string;
    import_bin_with_slot: ImportBinWithSlot[];
};

export type ExportTicket = {
    name: string;
    transporter: string;
    description: string;
    debt?: Debt;
    exportBins: ExportBin[];
    customer_id: number;
    customer_detail?: Partner;
    export_date: string;
};
