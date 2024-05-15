import { makeAuthRequest } from '../makeRequest';

type GetEmployeeListPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    name?: string;
    status?: string;
};

export const getEmployeeList = async ({
    token,
    pageIndex,
    pageSize = 10,
    name,
    status
}: GetEmployeeListPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/employees/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                name: name,
                status: status
            }
        });
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
