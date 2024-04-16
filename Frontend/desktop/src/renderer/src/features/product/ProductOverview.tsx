import { CgCloseR } from 'react-icons/cg';
import { ProductLine } from './type';
import ProductLineItem from './ProductLineItem';
import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

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
    const { productId } = useParams();
    const navigate = useNavigate();
    const [selected, setSelected] = useState(1);
    const selectProductLine = (lineId: number) => {
        setSelected(lineId);
        navigate(`/product/${productId}/line/${lineId}`);
    };
    return (
        <div className="h-full max-h-screen flex flex-col items-center relative border border-slate-300 px-5 py-5">
            <h1 className="text-xl font-semibold text-blue-700 mb-5">
                Tổng quan
            </h1>
            <CgCloseR
                className="cursor-pointer hover:bg-slate-300 absolute top-5 right-5"
                // onClick={onClose}
            />
            <div className="flex-col flex gap-5">
                {productLines.map((lineItem, i) => (
                    <ProductLineItem
                        {...lineItem}
                        key={i}
                        isSelected={i + 1 === selected}
                        selectLine={selectProductLine}
                        id={i + 1}
                    />
                ))}
            </div>
        </div>
    );
};

export default ProductOverview;
