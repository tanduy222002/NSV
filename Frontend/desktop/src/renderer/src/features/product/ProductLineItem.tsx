import fruitSrc from '@renderer/assets/fruit.png';
import { TiTag } from 'react-icons/ti';
import { LuWarehouse } from 'react-icons/lu';
import { FaArrowRight } from 'react-icons/fa6';

type ProductLineItemProps = {
    name: string;
    quantity: number;
    location: string;
};

const ProductLineItem = ({
    name,
    quantity,
    location
}: ProductLineItemProps) => {
    return (
        <div className="flex gap-3 items-center min-w-[350px] px-5 py-3 border border-[#1C274C] rounded-md">
            <div className="w-[40px] h-[30px]">
                <img
                    src={fruitSrc}
                    alt="product line image"
                    className="w-full h-full"
                />
            </div>

            <div className="flex-1">
                <div className="flex items-center gap-1 font-semibold">
                    <TiTag />
                    <p>
                        {name}: {quantity} kg
                    </p>
                </div>
                <div className="flex items-center gap-1 font-semibold">
                    <LuWarehouse />
                    <p>{location}</p>
                </div>
                <div className="curser-pointer font-semibold text-[#7C8DB5] flex items-center gap-1 ml-auto w-fit cursor-pointer">
                    <FaArrowRight />
                    Chi tiết
                </div>
            </div>
        </div>
    );
};

export default ProductLineItem;
