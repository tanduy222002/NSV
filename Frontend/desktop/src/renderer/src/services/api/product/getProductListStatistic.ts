import { makeAuthRequest } from '../makeRequest';

type GetProductListStatisticPayload = {
    token: string;
    pageIndex: number;
    pageSize: number;
    name?: string;
};

export const getProductListStatistic = async ({
    token,
    pageIndex = 1,
    pageSize = 10,
    name
}: GetProductListStatisticPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: '/products/statistic',
            method: 'get',
            params: {
                pageIndex,
                pageSize,
                name
            },
            token: token
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
