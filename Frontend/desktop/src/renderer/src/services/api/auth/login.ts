import { makeRequest } from '../makeRequest';

type LoginPayload = {
    username: string;
    password: string;
};

export const login = async ({ username, password }: LoginPayload) => {
    try {
        const response = await makeRequest({
            url: '/auth/sign-in',
            method: 'post',
            body: {
                user_name: username,
                password: password
            }
        });
        return response;
    } catch (err) {
        console.log('login error: ', err);
    }
};
