import { makeAuthRequestV2 } from '../makeRequest';
import { WarehouseMap, MapRow } from '@renderer/features/warehouse/type';

type CreateWarehouseMapPayload = {
    token: string;
    warehouseMap: WarehouseMap;
};

const preparePayload = (warehouseMap: WarehouseMap) => {
    const response = {
        name: warehouseMap.name,
        rowDtos: warehouseMap.rows.map((row: MapRow, i) => ({
            name: row.name,
            y_position: i,
            slotDtos: row.slots.map((slot, i) => ({
                name: slot.name,
                capacity: slot.capacity,
                description: slot.description,
                x_position: i
            }))
        }))
    };
    console.log('map response: ', response);
    return response;
};

export const createWarehouseMap = async ({
    token,
    warehouseMap
}: CreateWarehouseMapPayload) => {
    let response = undefined;
    try {
        response = await makeAuthRequestV2({
            token: token,
            url: '/maps',
            method: 'post',
            body: preparePayload(warehouseMap)
        });
    } catch (err) {
        console.log('create warehouse map error: ', err);
    }
    return response;
};
