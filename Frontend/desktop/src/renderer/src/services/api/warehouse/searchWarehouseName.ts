import { makeAuthRequest } from '../makeRequest';

type SearchWarehouseNamePayload = {
    token: string;
};

export const searchWarehouseName = async ({
    token
}: SearchWarehouseNamePayload) => {
    try {
        const response = await makeAuthRequest({
            token: token,
            url: `/warehouses/name_and_id`,
            method: 'get'
        });
        return response;
    } catch (err) {
        console.log('get warehouse name error: ', err);
    }
};
