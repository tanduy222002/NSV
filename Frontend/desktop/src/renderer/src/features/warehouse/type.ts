export type MapSlot = {
    name: string;
    capacity: number;
    description: string;
};

export type MapRow = {
    name: string;
    slots: MapSlot[];
};

export type WarehouseMap = {
    name: string;
    rows: MapRow[];
};
