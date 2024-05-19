import { makeAuthRequest } from '../makeRequest';

type GetPartnerDebtDetailPayload = {
    token: string;
    partnerId: string;
};

export const getPartnerDebtDetail = async ({
    token,
    partnerId
}: GetPartnerDebtDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/${partnerId}/debts`,
            method: 'get'
        });
        return response;
    } catch (err) {
        console.log('get partner debt error: ', err);
    }
};
