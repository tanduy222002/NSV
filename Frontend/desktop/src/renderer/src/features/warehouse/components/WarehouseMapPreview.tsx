import { WarehouseMap } from '../type';
import { AiOutlineCloseSquare } from 'react-icons/ai';
import { cn } from '@renderer/utils/util';

export enum MapViewMode {
    View,
    Edit,
    Select
}

type WarehouseMapPreviewProps = {
    warehouseMap: WarehouseMap;
    viewMode?: MapViewMode;
    deleteRow?: (index: number) => void;
    selectAction?: (slot: any) => void;
};

const WareHouseMapPreview = ({
    warehouseMap,
    viewMode = MapViewMode.View,
    deleteRow,
    selectAction
}: WarehouseMapPreviewProps) => {
    const handleDelete = (index: number) => {
        deleteRow && deleteRow(index);
    };

    return (
        <div className="flex flex-col items-center">
            {warehouseMap?.name.length > 0 && (
                <h1 className="font-semibold text-xl text-sky-800 mb-2">
                    {warehouseMap?.name}
                </h1>
            )}
            {warehouseMap.rows?.map((row, rowIndex) => (
                <div className="flex items-center gap-3 my-2" key={rowIndex}>
                    {row?.slots?.map((slot, slotIndex) => (
                        <div
                            className={cn(
                                'rounded-md border border-sky-800 text-sky-800 py-1 px-3 font-semibold',
                                slot.status !== 'EMPTY'
                                    ? 'bg-sky-300'
                                    : 'bg-white',
                                viewMode === MapViewMode.Select &&
                                    'cursor-pointer'
                            )}
                            key={slotIndex}
                            onClick={() =>
                                selectAction &&
                                selectAction({ rowIndex, slotIndex })
                            }
                        >
                            {slot.name}
                        </div>
                    ))}
                    {viewMode === MapViewMode.Edit && (
                        <AiOutlineCloseSquare
                            className="w-[20px] h-[20px] ml-auto text-slate-200 hover:text-red-500 cursor-pointer"
                            onClick={() => handleDelete(rowIndex)}
                        />
                    )}
                </div>
            ))}
        </div>
    );
};

export default WareHouseMapPreview;
