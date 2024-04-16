import fruitSrc from '@renderer/assets/fruit.png';
import { TiTag } from 'react-icons/ti';
import { LuWarehouse } from 'react-icons/lu';
import { FaArrowRight } from 'react-icons/fa6';
import { cn } from '@renderer/utils/util';

type ProductLineItemProps = {
    id: number;
    name: string;
    quantity: number;
    location: string;
    isSelected: boolean;
    selectLine: (lineId: number) => void;
};

const ProductLineItem = ({
    id,
    name,
    quantity,
    location,
    isSelected,
    selectLine
}: ProductLineItemProps) => {
    return (
        <div
            className={cn(
                'flex gap-3 items-center min-w-[350px] px-5 py-3 border border-blue-700 rounded-md cursor-pointer',
                isSelected ? 'bg-slate-100' : ''
            )}
            onClick={() => selectLine(id)}
        >
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
                <div className="flex items-center gap-1 font-semibold mb-1">
                    <LuWarehouse />
                    <p>{location}</p>
                </div>
                <div className="curser-pointer font-semibold text-slate-300 hover:text-slate-400 text-sm flex items-center gap-1 ml-auto w-fit cursor-pointer">
                    <FaArrowRight />
                    Chi tiết
                </div>
            </div>
        </div>
    );
};

export default ProductLineItem;
