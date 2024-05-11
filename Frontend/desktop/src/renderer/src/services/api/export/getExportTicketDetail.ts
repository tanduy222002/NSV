import { makeAuthRequest } from '../makeRequest';

type GetExportTicketDetailPayload = {
    token: string;
    ticketId: number | string;
};

export const getExportTicketDetail = async ({
    token,
    ticketId
}: GetExportTicketDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/${ticketId}`,
            method: 'get'
        });
        console.log('export tickets: ', response);
        return response;
    } catch (err) {
        console.log('get export ticket detail error: ', err);
    }
};
