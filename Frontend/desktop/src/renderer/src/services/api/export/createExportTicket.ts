import { ExportTicket } from '@renderer/types/export';
import { makeAuthRequest } from '../makeRequest';

type CreateExportTicketPayload = {
    token: string;
    ticket: ExportTicket;
};

export const createExportTicket = async ({
    token,
    ticket
}: CreateExportTicketPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/export_tickets`,
            method: 'post',
            body: {
                name: ticket?.name,
                transporter: ticket?.transporter,
                description: ticket?.description,
                debtDto: ticket?.debt ? { ...ticket?.debt } : undefined,
                exportBinDto: [
                    ...ticket.exportBins.map((exportBin) => ({
                        weight: exportBin?.weight,
                        price: exportBin?.price,
                        note: exportBin?.note,
                        quality_id: exportBin?.quality_id,
                        package_type: exportBin?.package_type,
                        import_bin_with_slot: [
                            ...exportBin!.import_bin_with_slot
                        ]
                    }))
                ]
            }
        });
        return response;
    } catch (err) {
        console.log('create export ticket error: ', err);
    }
};
