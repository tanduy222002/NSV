import { makeAuthRequest } from '../makeRequest';

type GetWardsPayload = {
    provinceId: number;
    districtId: number;
    token: string;
};

export const getWards = async ({
    token,
    provinceId,
    districtId
}: GetWardsPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/location/provinces/${provinceId}/districts/${districtId}/wards`,
            method: 'get'
        });
    } catch (err) {
        console.log('get districts error: ', err);
    }
    return response;
};
