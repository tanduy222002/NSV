import { makeAuthRequest } from '../makeRequest';

type GetPartnerDebtDetailPayload = {
    token: string;
    partnerId: string;
    isPaid?: boolean;
};

export const getPartnerDebtDetail = async ({
    token,
    partnerId,
    isPaid
}: GetPartnerDebtDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/${partnerId}/debts`,
            method: 'get',
            params: {
                isPaid: isPaid
            }
        });
        return response;
    } catch (err) {
        console.log('get partner debt error: ', err);
    }
};
