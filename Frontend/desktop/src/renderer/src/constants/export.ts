import { ExportTicket } from '@renderer/types/export';

export const defaultExportTicketValue: ExportTicket = {
    name: '',
    transporter: '',
    description: '',
    debt: undefined,
    exportBins: [],
    customer_id: 0,
    export_date: ''
};
