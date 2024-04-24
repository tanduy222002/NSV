import { makeAuthRequest } from '../makeRequest';

type GetProvincesPayload = {
    token: string;
};

export const getProvinces = async ({
    token
}: GetProvincesPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: '/location/provinces',
            method: 'get'
        });
    } catch (err) {
        console.log('get provinces error: ', err);
    }
    return response;
};
