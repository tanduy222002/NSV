import { makeAuthRequest } from '../makeRequest';

type RemoveTicketDebtPayload = {
    token: string;
    ticketId: string;
};

export const removeTicketDebt = async ({
    token,
    ticketId
}: RemoveTicketDebtPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/debts/${ticketId}`,
            method: 'put',
            body: {
                is_paid: true
            }
        });
        return response;
    } catch (err) {
        console.log('clear ticket debt error: ', err);
    }
};
