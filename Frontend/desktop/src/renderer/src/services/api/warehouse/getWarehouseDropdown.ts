import { makeAuthRequest } from '../makeRequest';

type GetWarehouseDropdownPayload = {
    token: string;
};

export const getWarehouseDropdown = async ({
    token
}: GetWarehouseDropdownPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/warehouses/name_and_id`,
            method: 'get'
        });

        console.log('response: ', response);
        return response;
    } catch (err) {
        console.log('get warehouse dropdown error: ', err);
    }
};
