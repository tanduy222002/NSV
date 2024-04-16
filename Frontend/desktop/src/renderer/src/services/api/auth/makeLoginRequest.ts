import { makeRequest } from '../makeRequest';

type LoginPayload = {
    username: string;
    password: string;
};

export const makeLoginRequest = async ({
    username,
    password
}: LoginPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/auth/sign-in',
            method: 'post',
            body: {
                user_name: username,
                password: password
            }
        });
    } catch (err) {
        console.log('login error: ', err);
    }
    return response;
};
