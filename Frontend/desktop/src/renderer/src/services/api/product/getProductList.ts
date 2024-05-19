import { makeAuthRequest } from '../makeRequest';

type ProductListResponse =
    | {
          page: number;
          content: any[];
          total_element: number;
          total_page: number;
      }
    | undefined;

type GetProductListPayload = {
    token: string;
    pageIndex?: number;
    pageSize?: number;
};

export const getProductList = async ({
    token,
    pageIndex = 1,
    pageSize = 10
}: GetProductListPayload): Promise<any> => {
    let response: ProductListResponse = undefined;
    try {
        response = await makeAuthRequest({
            url: '/products',
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
