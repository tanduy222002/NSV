import { makeAuthRequestV2 } from '../makeRequest';

type UpdateEmployeeAccountStatusPayload = {
    token: string;
    employeeId: string;
    status: string;
};

export const updateEmployeeAccountStatus = async ({
    token,
    employeeId,
    status
}: UpdateEmployeeAccountStatusPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequestV2({
            token: token,
            url: `/employees/${employeeId}/status`,
            method: 'put',
            body: {
                status: status
            }
        });
        return response;
    } catch (err) {
        console.log('update employee account status error: ', err);
    }
};
