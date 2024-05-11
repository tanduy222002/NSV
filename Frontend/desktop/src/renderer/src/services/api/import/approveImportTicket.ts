import { makeAuthRequest } from '../makeRequest';

type ApproveImportTicketPayload = {
    token: string;
    ticketId: number | string;
};

export const approveImportTicket = async ({
    token,
    ticketId
}: ApproveImportTicketPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/import_tickets/${ticketId}/status/approve`,
            method: 'put'
        });
        return response;
    } catch (err) {
        console.log('approve import ticket error: ', err);
    }
};
