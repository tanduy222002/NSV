import { makeRequest } from '../makeRequest';

type OtpConfirmationMethod = 'email' | 'sms';

type GenerateOtpPayload = {
    username: string;
    deliveryMethod: OtpConfirmationMethod;
};

// get new access token
export const makeGenerateOtpRequest = async (payload: GenerateOtpPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/api/auth/request-otp',
            method: 'post',
            body: payload
        });
    } catch (err) {
        console.log('error: ', err);
    }
    return response;
};
