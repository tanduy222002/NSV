import { makeAuthRequest } from '../makeRequest';

type GetWarehouseCategoryPayload = {
    token: string;
};

export const getWarehouseCategory = async ({
    token
}: GetWarehouseCategoryPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: '/warehouses/variety',
            method: 'get'
        });
    } catch (err) {
        console.log('get warehouse category error: ', err);
    }
    return response;
};
