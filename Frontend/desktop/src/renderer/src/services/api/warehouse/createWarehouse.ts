import { makeAuthRequest } from '../makeRequest';

type CreateWarehousePayload = {
    token: string;
    body: {
        name: string;
        type: string;
        mapId: number;
        address: {
            address: string;
            wardId: number;
            districtId: number;
            provinceId: number;
        };
    };
};

export const createWarehouse = async ({
    token,
    body
}: CreateWarehousePayload) => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: '/warehouses',
            method: 'post',
            body: body
        });
    } catch (err) {
        console.log('create warehouse error: ', err);
    }
    return response;
};
