import { makeAuthRequest } from '../makeRequest';

type GetUnusedMapPayload = {
    token: string;
};

export const getUnusedMap = async ({ token }: GetUnusedMapPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/maps/unused`,
            method: 'get'
        });
    } catch (err) {
        console.log('get unused map error: ', err);
    }
    return response;
};
