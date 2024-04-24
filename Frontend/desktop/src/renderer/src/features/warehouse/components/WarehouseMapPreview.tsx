import { WarehouseMap } from '../type';
import { AiOutlineCloseSquare } from 'react-icons/ai';

export enum MapViewMode {
    View,
    Edit
}

type WarehouseMapPreviewProps = {
    warehouseMap: WarehouseMap;
    viewMode?: MapViewMode;
    deleteRow?: (index: number) => void;
};

const WareHouseMapPreview = ({
    warehouseMap,
    viewMode = MapViewMode.View,
    deleteRow
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
            {warehouseMap.rows?.map((row, index) => (
                <div className="flex items-center gap-3 my-2" key={index}>
                    {row?.slots?.map((slot, index) => (
                        <div
                            className="rounded-md border border-[#1A3389] text-[#1A3389] py-1 px-3 font-semibold"
                            key={index}
                        >
                            {slot.name}
                        </div>
                    ))}
                    {viewMode === MapViewMode.Edit && (
                        <AiOutlineCloseSquare
                            className="w-[20px] h-[20px] ml-auto text-slate-200 hover:text-red-500 cursor-pointer"
                            onClick={() => handleDelete(index)}
                        />
                    )}
                </div>
            ))}
        </div>
    );
};

export default WareHouseMapPreview;
