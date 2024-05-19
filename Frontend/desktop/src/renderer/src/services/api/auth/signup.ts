import { makeRequest } from '../makeRequest';

type SignupPayload = {
    username: string;
    password: string;
    email: string;
    roles: string[];
};

export const signup = async ({
    username,
    password,
    email,
    roles = ['ROLE_EMPLOYEE', 'ROLE_MANAGER']
}: SignupPayload) => {
    try {
        const response = await makeRequest({
            url: '/auth/sign-up',
            method: 'post',
            body: {
                user_name: username,
                email: email,
                roles: roles,
                password: password,
                confirm_password: password
            }
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
