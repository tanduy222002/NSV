import { Address } from './common';

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
