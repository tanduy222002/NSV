import { makeAuthRequest } from '../makeRequest';

type GetProductTypePayload = {
    token: string;
};

export const getProductType = async ({
    token
}: GetProductTypePayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: `/products/variety`,
            method: 'get',
            token: token
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
