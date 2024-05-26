import { makeAuthRequestV2 } from '../makeRequest';

type LogoutPayload = {
    token: string;
};

export const logout = async ({ token }: LogoutPayload) => {
    try {
        const response = await makeAuthRequestV2({
            url: '/auth/log-out',
            method: 'get',
            token: token
        });
        return response;
    } catch (err) {
        console.log('logout error: ', err);
    }
};
