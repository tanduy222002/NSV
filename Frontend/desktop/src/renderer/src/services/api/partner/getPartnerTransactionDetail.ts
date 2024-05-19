import { makeAuthRequest } from '../makeRequest';

type GetPartnerTransactionDetailPayload = {
    token: string;
    partnerId: string;
    isPaid?: boolean;
};

export const getPartnerTransactionDetail = async ({
    token,
    partnerId,
    isPaid
}: GetPartnerTransactionDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/${partnerId}/transactions`,
            method: 'get',
            params: {
                isPaid: isPaid
            }
        });
        return response;
    } catch (err) {
        console.log('get partner transaction error: ', err);
    }
};
