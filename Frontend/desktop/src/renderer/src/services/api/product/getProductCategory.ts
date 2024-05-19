import { makeAuthRequest } from '../makeRequest';

type GetProductCategoryPayload = {
    token: string;
    productId: number;
};

export const getProductCategory = async ({
    token,
    productId
}: GetProductCategoryPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: `/products/${productId}/quality_with_type`,
            method: 'get',
            token: token
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
