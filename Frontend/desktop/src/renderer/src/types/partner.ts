import { Location } from './common';

export type Address = {
    address: string;
    ward: Location | null;
    district: Location | null;
    province: Location | null;
};

export type Partner = {
    name: string;
    email: string;
    address?: Address;
    phoneNumber: string;
    taxNumber: string;
    phone: string;
    faxNumber: string;
    bankAccount: string;
};
