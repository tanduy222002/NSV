import { ImportTicket } from '@renderer/types/import';

export const defaultImportTicketValue: ImportTicket = {
    name: '',
    weight: 0,
    transporter: '',
    description: '',
    value: 0,
    debtDto: null,
    binDto: [],
    provider_id: null,
    import_date: '',
    transport_date: ''
};
