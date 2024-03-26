import { makeRequest } from '../makeRequest';

type SignupPayload = {
    username: string;
    password: string;
    email: string;
    roles: string[];
};

export const makeSignupRequest = async ({
    username,
    password,
    email,
    roles = ['ROLE_EMPLOYEE', 'ROLE_MANAGER']
}: SignupPayload) => {
    console.log('payload: ', username, password, email);
    let response = undefined;
    try {
        response = await makeRequest({
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
        console.log('res: ', response);
    } catch (err) {
        console.log('error: ', err);
    }
    return response;
};
