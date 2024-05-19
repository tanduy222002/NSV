import { makeAuthRequest } from '../makeRequest';

type RemoveTicketDebtPayload = {
    token: string;
    debtId: string;
};

export const removeTicketDebt = async ({
    token,
    debtId
}: RemoveTicketDebtPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/debts/${debtId}`,
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
