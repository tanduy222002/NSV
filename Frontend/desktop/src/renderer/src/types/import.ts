import { Partner } from './partner';

export enum TicketStatus {
    Approved = 'APPROVED',
    Pending = 'PENDING',
    Rejected = 'REJECTED',
    All = 'ALL'
}

export type BinWithSlot = {
    weight: number;
    slot_id: number;
    area: number;
};

type DebtDto = {
    name: string;
    value: number;
    description: string;
    unit: string;
    due_date: string;
};

export type BinDto = {
    weight: number;
    binWithSlot: BinWithSlot[];
    price: number;
    note: string;
    quality_id: number;
    quality_detail: any;
    package_type: string;
};

export enum DebtOption {
    Yes = 'Có',
    No = 'Không'
}

export type ImportTicket = {
    name: string;
    weight: number;
    transporter: string;
    description: string;
    value: number;
    debtDto?: DebtDto | null;
    binDto: BinDto[];
    provider_id: number | null;
    provider_detail?: Partner;
    import_date: string;
    transport_date: string;
};
