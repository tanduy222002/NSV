import { makeAuthRequest } from '../makeRequest';

type GetDistrictPayload = {
    provinceId: number;
    token: string;
};

export const getDistricts = async ({
    token,
    provinceId
}: GetDistrictPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/location/provinces/${provinceId}/districts`,
            method: 'get'
        });
    } catch (err) {
        console.log('get districts error: ', err);
    }
    return response;
};
