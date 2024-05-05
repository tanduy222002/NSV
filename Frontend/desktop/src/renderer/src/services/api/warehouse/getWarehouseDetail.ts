import { makeAuthRequest } from '../makeRequest';

type GetWarehouseDetailPayload = {
    token: string;
    warehouseId: number;
};

export const getWarehouseDetail = async ({
    token,
    warehouseId
}: GetWarehouseDetailPayload) => {
    let response: any = undefined;
    try {
        const warehouseDetail = await makeAuthRequest({
            token: token,
            url: `/warehouses/${warehouseId}`,
            method: 'get'
        });
        console.log('before map: ', warehouseDetail);
        response = {
            ...warehouseDetail,
            map: {
                rows: warehouseDetail?.map?.rows?.map((row) => ({
                    name: row?.name,
                    slots: row?.slots?.map((slot) => ({
                        id: slot?.id,
                        name: slot?.name,
                        capacity: slot?.capacity,
                        status: slot?.status,
                        currentLoad: slot?.containing,
                        description: slot?.description
                    }))
                }))
            }
        };
        console.log('response: ', response);
        return response;
    } catch (err) {
        console.log('get warehouse detail error: ', err);
    }
};
