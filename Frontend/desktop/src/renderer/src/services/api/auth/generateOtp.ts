import { makeRequest } from '../makeRequest';

type OtpConfirmationMethod = 'email' | 'sms';

type GenerateOtpPayload = {
    identifier: string; // email or phone number depend on confirmation method
    deliveryMethod: OtpConfirmationMethod;
};

// get new access token
export const generateOtp = async (payload: GenerateOtpPayload) => {
    let response = undefined;
    try {
        response = await makeRequest({
            url: '/auth/request-otp',
            method: 'post',
            body: {
                identifier: payload.identifier,
                deliveryMethod: payload.deliveryMethod
            }
        });
    } catch (err) {
        console.log('error: ', err);
    }
    return response;
};
