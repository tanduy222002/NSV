import { makeAuthRequest } from '../makeRequest';
import { ImportTicket } from '@renderer/types/import';

type CreateImportTicketPayload = {
    token: string;
    ticket: ImportTicket;
};

export const createImportTicket = async ({
    token,
    ticket
}: CreateImportTicketPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/import_tickets`,
            method: 'post',
            body: {
                name: ticket?.name,
                transporter: ticket?.transporter,
                description: ticket?.description,
                debtDto: ticket?.debtDto
                    ? {
                          ...ticket?.debtDto
                      }
                    : undefined,
                binDto: ticket.binDto.map((bin) => ({
                    weight: bin?.weight,
                    binWithSlot: [...bin.binWithSlot],
                    price: bin?.price,
                    note: bin?.note,
                    quality_id: bin?.quality_id,
                    package_type: bin?.package_type
                })),
                provider_id: ticket?.provider_id,
                import_date: ticket?.import_date
            }
        });
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
