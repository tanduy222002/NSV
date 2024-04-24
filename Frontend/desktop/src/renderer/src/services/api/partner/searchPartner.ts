import { makeAuthRequest } from '../makeRequest';

type SearchPartnerPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    phoneNumber?: string;
};

const mapResponse = (response: any) => {
    return {
        totalPage: response?.total_page,
        content: response?.content.map((item) => ({
            id: item?.id,
            name: item?.name,
            phone: item?.phone,
            address: `${item?.address?.name}, ${item?.address?.ward?.name}, ${item?.address?.ward?.district?.name}, ${item?.address?.ward?.district?.province?.name}`
        }))
    };
};

export const searchPartner = async ({
    token,
    pageIndex,
    pageSize = 12,
    name,
    phoneNumber
}: SearchPartnerPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/partners/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                name: name,
                phone: phoneNumber
            }
        });
        console.log('partners before map: ', response);
        response = mapResponse(response);
        console.log('partners: ', response);
    } catch (err) {
        console.log('get partner error: ', err);
    }
    return response;
};
