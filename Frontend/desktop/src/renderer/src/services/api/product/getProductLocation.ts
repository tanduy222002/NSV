import { makeAuthRequest } from '../makeRequest';

type GetProductLocationPayload = {
    token: string;
    productId: number | string;
    pageIndex: number;
    pageSize: number;
};

export const getProductLocation = async ({
    token,
    productId,
    pageIndex = 1,
    pageSize = 10
}: GetProductLocationPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: `/products/${productId}/statistic`,
            method: 'get',
            params: {
                pageIndex,
                pageSize
            },
            token: token
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
