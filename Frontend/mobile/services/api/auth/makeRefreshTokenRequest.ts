import { makeRequest } from '../makeRequest';

type RefreshTokenPayload = {
    refreshToken: string;
};

// get new access token
export const makeLoginRequest = async (payload: RefreshTokenPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/auth/refresh-token',
            method: 'post',
            body: payload
        });
    } catch (err) {
        console.log('error: ', err);
    }
    return response;
};
