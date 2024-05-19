export type MapSlot = {
    name: string;
    capacity: number;
    description: string;
    status: string;
    curentLoad: number;
};

export type MapRow = {
    name: string;
    slots: MapSlot[];
};

export type WarehouseMap = {
    name: string;
    rows: MapRow[];
};
