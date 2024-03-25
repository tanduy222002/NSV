import { makeRequest } from '../makeRequest';

type SignupPayload = {
    userName: string;
    password: string;
};

export const makeSignupRequest = async (payload: SignupPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/api/auth/sign-up',
            method: 'post',
            body: payload
        });
    } catch (err) {
        console.log('login error: ', err);
    }
    return response;
};
