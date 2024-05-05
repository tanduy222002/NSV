export type Product = {
    name: string;
    variety: string;
    image?: string;
    categories: ProductCategory[];
};

export type Quality = {
    name: string;
    description: string;
};

export type ProductCategory = {
    name: string;
    seasonal: string;
    image?: string;
    lower_temperature_threshold: string;
    upper_temperature_threshold: string;
    qualities: Quality[];
};
