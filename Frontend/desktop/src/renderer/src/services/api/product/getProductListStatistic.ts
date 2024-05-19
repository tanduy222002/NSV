import { makeAuthRequest } from '../makeRequest';

type GetProductListStatisticPayload = {
    token: string;
    pageIndex: number;
    pageSize: number;
};

export const getProductListStatistic = async ({
    token,
    pageIndex = 1,
    pageSize = 10
}: GetProductListStatisticPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: '/products/statistic',
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
