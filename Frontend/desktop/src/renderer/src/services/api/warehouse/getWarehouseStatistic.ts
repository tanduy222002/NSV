import { makeAuthRequest } from '../makeRequest';

type GetWarehouseStatisticPayload = {
    token: string;
    id: number;
};

export const getWarehouseStatistic = async ({
    token,
    id
}: GetWarehouseStatisticPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/warehouses/${id}/products/statistics`,
            method: 'get'
        });
        console.log('response: ', response);
        return response;
    } catch (err) {
        console.log('get warehouse detail error: ', err);
    }
};
