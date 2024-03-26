import { makeRequest } from '../makeRequest';

type LoginPayload = {
    userName: string;
    password: string;
};

export const makeLoginRequest = async (payload: LoginPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/auth/sign-in',
            method: 'post',
            body: payload
        });
    } catch (err) {
        console.log('login error: ', err);
    }
    return response;
};
