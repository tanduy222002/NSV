import { makeAuthRequestV2 } from '../makeRequest';

type UpdatePartnerProfilePayload = {
    token: string;
    partnerId: string;
    profile: any;
};

export const updatePartnerProfile = async ({
    token,
    partnerId,
    profile
}: UpdatePartnerProfilePayload): Promise<any> => {
    try {
        const response = await makeAuthRequestV2({
            token: token,
            url: `/partners/${partnerId}/profile`,
            method: 'put',
            body: profile
        });
        return response;
    } catch (err) {
        console.log('update partner profile error: ', err);
    }
};
