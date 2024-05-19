import { makeAuthRequest } from '../makeRequest';

type GetSlotDetailPayload = {
    token: string;
    warehouseId: number;
    slotId: number;
};

export const getSlotDetail = async ({
    token,
    warehouseId,
    slotId
}: GetSlotDetailPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/warehouses/${warehouseId}/slots/${slotId}/bins`,
            method: 'get'
        });
    } catch (err) {
        console.log('get slot detail error: ', err);
    }
    return response;
};
