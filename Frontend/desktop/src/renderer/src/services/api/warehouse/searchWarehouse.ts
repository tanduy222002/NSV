import { makeAuthRequest } from '../makeRequest';

type SearchWarehousePayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    type?: string;
    status?: string;
};

export const searchWarehouse = async ({
    token,
    pageIndex = 1,
    pageSize = 10,
    name,
    type,
    status
}: SearchWarehousePayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/warehouses/search`,
            method: 'get',
            params: {
                pageIndex,
                pageSize,
                name,
                type,
                status
            }
        });

        console.log('response: ', response);
        return response;
    } catch (err) {
        console.log('get warehouse detail error: ', err);
    }
};
