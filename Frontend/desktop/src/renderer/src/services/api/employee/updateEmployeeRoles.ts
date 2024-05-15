import { makeAuthRequestV2 } from '../makeRequest';

type UpdateEmployeeRolesPayload = {
    token: string;
    employeeId: string;
    role: string;
};

export const updateEmployeeRoles = async ({
    token,
    employeeId,
    role
}: UpdateEmployeeRolesPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequestV2({
            token: token,
            url: `/employees/${employeeId}/roles`,
            method: 'put',
            body: {
                role: role
            }
        });
        return response;
    } catch (err) {
        console.log('update employee roles error: ', err);
    }
};
