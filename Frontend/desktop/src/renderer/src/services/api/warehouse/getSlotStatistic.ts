import { makeAuthRequest } from '../makeRequest';

type GetSlotStatisticPayload = {
    token: string;
    warehouseId: number;
    slotId: number;
};

export const getSlotStatistic = async ({
    token,
    warehouseId,
    slotId
}: GetSlotStatisticPayload) => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/warehouses/${warehouseId}/slots/${slotId}/statistics`,
            method: 'get'
        });
    } catch (err) {
        console.log('get warehouse category error: ', err);
    }
    return response;
};
