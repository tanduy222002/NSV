import { makeAuthRequest } from '../makeRequest';

type GetAvailableBinPayload = {
    token: string;
    pageIndex: number;
    pageSize?: number;
    warehouseId?: number;
    typeId?: number;
    qualityId?: number;
    minWeight?: number;
    maxWeight?: number;
};

export const getAvailableBin = async ({
    token,
    pageIndex,
    pageSize = 12,
    warehouseId,
    qualityId
}: GetAvailableBinPayload): Promise<any> => {
    let response: any = undefined;
    try {
        response = await makeAuthRequest({
            token: token,
            url: `/bins/search`,
            method: 'get',
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
                warehouseId: warehouseId,
                qualityId: qualityId
            }
        });
        console.log('export tickets: ', response);
        return response;
    } catch (err) {
        console.log('get partner error: ', err);
    }
};
