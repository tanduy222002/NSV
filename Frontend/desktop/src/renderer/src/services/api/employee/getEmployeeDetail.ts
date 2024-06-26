import { makeAuthRequest } from '../makeRequest';

type GetEmployeeDetailPayload = {
    token: string;
    employeeId: string;
};

export const getEmployeeDetail = async ({
    token,
    employeeId
}: GetEmployeeDetailPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/employees/${employeeId}`,
            method: 'get'
        });
        return response;
    } catch (err) {
        console.log('get employee detail error: ', err);
    }
};
