import { makeAuthRequest } from '../makeRequest';

type ApproveExportTicketPayload = {
    token: string;
    ticketId: number | string;
};

export const approveExportTicket = async ({
    token,
    ticketId
}: ApproveExportTicketPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/export_tickets/${ticketId}/status/approve`,
            method: 'put'
        });
        return response;
    } catch (err) {
        console.log('approve export ticket error: ', err);
    }
};
