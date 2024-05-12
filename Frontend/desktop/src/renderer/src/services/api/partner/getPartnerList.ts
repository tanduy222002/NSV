import { makeAuthRequest } from '../makeRequest';

type GetPartnerListPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    phoneNumber?: string;
};

export const getPartnerList = async ({
    token,
    pageIndex,
    pageSize = 12,
    name,
    phoneNumber
}: GetPartnerListPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/statistic/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                name: name,
                phone: phoneNumber
            }
        });
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
