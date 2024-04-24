import { makeAuthRequest } from '../makeRequest';
import { WarehouseMap } from '@renderer/features/warehouse/type';

type GetWarehouseMapPayload = {
    mapId: number;
    token: string;
};

export const getWarehouseMap = async ({
    token,
    mapId
}: GetWarehouseMapPayload) => {
    let response: WarehouseMap | null = null;
    try {
        const map = await makeAuthRequest({
            token: token,
            url: `/maps/${mapId}`,
            method: 'get'
        });

        response = {
            name: map.name,
            rows: map.rowDtos.map((row) => ({
                name: row.name,
                slots: row.slotDtos.map((slot) => ({
                    name: slot.name,
                    capacity: slot.capacity,
                    description: slot.description
                }))
            }))
        };
        console.log('map response: ', response);
    } catch (err) {
        console.log('get warehouse map error: ', err);
    }
    return response;
};
