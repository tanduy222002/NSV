import { makeAuthRequestV2 } from '../makeRequest';

type DeleteEmployeeListPayload = {
    token: string;
    employeeId: string;
};

export const deleteEmployee = async ({
    token,
    employeeId
}: DeleteEmployeeListPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequestV2({
            token: token,
            url: `/employees/${employeeId}`,
            method: 'delete'
        });
        return response;
    } catch (err) {
        console.log('delete employee error: ', err);
    }
};
