import { makeAuthRequest } from '../makeRequest';

type GetImportTicketDetailPayload = {
    token: string;
    ticketId: number | string;
};

export const getImportTicketDetail = async ({
    token,
    ticketId
}: GetImportTicketDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/transfer_ticket/${ticketId}`,
            method: 'get'
        });
        console.log('import tickets: ', response);
        return response;
    } catch (err) {
        console.log('get import ticket detail error: ', err);
    }
};
