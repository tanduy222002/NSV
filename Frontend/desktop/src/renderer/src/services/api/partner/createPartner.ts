import { Partner } from '@renderer/types/partner';
import { makeAuthRequestV2 } from '../makeRequest';

type createPartnerPayload = {
    token: string;
    partner: Partner;
};

export const createPartner = async ({
    token,
    partner
}: createPartnerPayload): Promise<any> => {
    try {
        const response = await makeAuthRequestV2({
            token: token,
            url: `/partners`,
            method: 'post',
            body: {
                name: partner?.name,
                email: partner?.email,
                address: {
                    address: partner?.address?.address,
                    wardId: partner?.address?.ward?.id,
                    districtId: partner?.address?.district?.id,
                    provinceId: partner?.address?.province?.id
                },
                phone_number: partner?.phoneNumber,
                tax_number: partner?.taxNumber,
                fax_number: partner?.faxNumber,
                bank_acount: partner?.bankAccount
            }
        });
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
