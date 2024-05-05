import { makeAuthRequest } from '../makeRequest';
import { Product } from '@renderer/types/product';

type GetProductCategoryPayload = {
    token: string;
    product: Product;
};

export const createProduct = async ({
    token,
    product
}: GetProductCategoryPayload): Promise<any> => {
    let response = undefined;
    try {
        response = await makeAuthRequest({
            url: `/products/products/types/qualities`,
            method: 'post',
            token: token,
            body: {
                product: {
                    name: product?.name,
                    variety: product?.variety,
                    image: product?.image
                },
                types: product?.categories.map((category) => ({
                    name: category?.name,
                    seasonal: category?.seasonal,
                    image: category?.image,
                    lower_temperature_threshold:
                        category?.lower_temperature_threshold,
                    upper_temperature_threshold:
                        category?.upper_temperature_threshold,
                    qualities: [...category.qualities]
                }))
            }
        });
        return response;
    } catch (err) {
        console.log('error: ', err);
    }
};
