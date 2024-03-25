import { ProductLine } from './type';
import ProductLineItem from './ProductLineItem';

// type ProductOvewviewProps = {
//     productLines: ProductLine[];
// };

const productLines: ProductLine[] = [
    {
        name: 'Ri6 loại 1',
        quantity: 5000,
        location: 'Vị trí: 3 khu vực đang chứa'
    },
    {
        name: 'Ri6 loại 2',
        quantity: 3000,
        location: 'Vị trí: 2 khu vực đang chứa'
    },
    {
        name: 'Dona loại 1',
        quantity: 5000,
        location: 'Vị trí: 3 khu vực đang chứa'
    },
    {
        name: 'Dona loại 2',
        quantity: 2500,
        location: 'Vị trí: 3 khu vực đang chứa'
    }
];

const ProductOverview = () => {
    return (
        <div className="h-full max-h-screen flex flex-col">
            <h1 className="text-xl font-semibold text-[#1A3389] mb-5">
                Tổng quan
            </h1>
            <div className="flex-col flex gap-5">
                {productLines.map((lineItem, index) => (
                    <ProductLineItem {...lineItem} key={index} />
                ))}
            </div>
        </div>
    );
};

export default ProductOverview;
