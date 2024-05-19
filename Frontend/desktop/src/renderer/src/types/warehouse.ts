type Address = {
    id: number;
    name: string;
    ward: {
        id: number;
        name: string;
        district: {
            id: number;
            name: string;
            province: {
                id: number;
                name: string;
            };
        };
    };
};

export type Warehouse = {
    id: number;
    name: string;
    type: string;
    address: Address;
    containing: number;
    capacity: number;
    status: string;
    address_string: string;
    current_capacity: string;
};
