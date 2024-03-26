import { makeRequest } from '../makeRequest';

type RenewPasswordPayload = {
    otp: string;
    identifier: string;
    password: string;
};

// get new access token
export const makeRenewPasswordRequest = async ({
    otp,
    identifier,
    password
}: RenewPasswordPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/auth/reset-password',
            method: 'post',
            body: {
                code: otp,
                identifier: identifier,
                new_password: password,
                confirm_new_password: password
            }
        });
    } catch (err) {
        console.log('error: ', err);
    }
    return response;
};
