import { makeAuthRequest } from '../makeRequest';

type ProductListResponse =
    | {
          page: number;
          content: any[];
          total_element: number;
          total_page: number;
      }
    | undefined;

export const makeGetProductListRequest = async (
    token: string,
    pageIndex: number = 1,
    pageSize: number = 10
): Promise<any> => {
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
        console.log('Product: ', response);
        return response?.content;
    } catch (err) {
        console.log('error: ', err);
    }
};
