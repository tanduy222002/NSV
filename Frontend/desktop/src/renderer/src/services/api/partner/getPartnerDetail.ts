import { makeAuthRequest } from '../makeRequest';

type GetPartnerDetailPayload = {
    token: string;
    partnerId: string;
};

export const getPartnerDetail = async ({
    token,
    partnerId
}: GetPartnerDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/${partnerId}`,
            method: 'get'
        });
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
