import { makeAuthRequestV2 } from '../makeRequest';

type UpdateEmployeeProfilePayload = {
    token: string;
    employeeId: number;
    profile: any;
};

export const updateEmployeeProfile = async ({
    token,
    employeeId,
    profile
}: UpdateEmployeeProfilePayload): Promise<any> => {
    try {
        console.log('profile: ', profile);
        const response = await makeAuthRequestV2({
            token: token,
            url: `/employees/${employeeId}/profile`,
            method: 'put',
            body: {
                name: profile?.name,
                gender: profile?.gender,
                email: profile?.email,
                avatar: profile?.avatar,
                phone_number: profile?.phoneNumber,
                addresses: {
                    address: profile?.addresses?.address,
                    wardId: profile?.addresses?.wardId,
                    districtId: profile?.addresses?.districtId,
                    provinceId: profile?.addresses?.provinceId
                }
            }
        });
        return response;
    } catch (err) {
        console.log('update employee profile error: ', err);
    }
};
