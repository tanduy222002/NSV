import { makeAuthRequest } from '../makeRequest';

type SearchWarehouseMapPayload = {
    token: string;
};

export const searchWarehouseMap = async ({
    token
}: SearchWarehouseMapPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: '/maps/search',
            method: 'get',
            params: {
                pageIndex: 1,
                pageSize: 10
            }
        });
    } catch (err) {
        console.log('search warehouse map error: ', err);
    }
    return response?.content;
};
