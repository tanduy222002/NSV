import { useRef } from 'react';
import { ImportBinWithSlot } from '@renderer/types/export';
import { GoMoveToEnd } from 'react-icons/go';
import { IoClose } from 'react-icons/io5';
import { cn } from '@renderer/utils/util';

type SlotTableProps = {
    slots: ImportBinWithSlot[];
    updateExportBins: (bins: ImportBinWithSlot[]) => void;
};

const SlotTable = ({ slots, updateExportBins }: SlotTableProps) => {
    const weightRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);
    const loadToExportTicket = ({
        id,
        weight,
        area
    }: {
        id: number;
        weight: number;
        area: number;
    }) => {
        const updatedBins = slots.map((slot, slotId) =>
            slotId === id
                ? {
                      ...slot,
                      id: slotId,
                      in_slot_weight: slot.in_slot_weight - weight,
                      taken_area: area,
                      taken_weight: weight
                  }
                : { ...slot, id: slotId }
        );
        updateExportBins(updatedBins);

        weightRef!.current!.value = '';
        areaRef!.current!.value = '';
    };
    const unloadFromExportTicket = ({ id }: { id: number }) => {
        const updatedBins = slots.map((slot, slotId) =>
            slotId === id
                ? {
                      ...slot,
                      in_slot_weight: slot.in_slot_weight + slot.taken_weight,
                      taken_area: 0,
                      taken_weight: 0
                  }
                : { ...slot }
        );
        updateExportBins(updatedBins);
    };

    return (
        <table className="w-fit min-w-[750px] h-fit border-collapse table-auto">
            <tr className="border border-1 text-sky-800">
                <th className="px-2 py-2">ID</th>
                <th className="px-2 py-2">Sản phẩm</th>
                <th className="px-2 py-2">Loại hàng</th>
                <th className="px-2 py-2">Quy cách</th>
                <th className="px-2 py-2">Khối lượng</th>
                <th className="px-2 py-2">Vị trí</th>
                <th className="px-2 py-2">Đã lấy</th>
                <th className="px-2 py-2">Khối lượng</th>
                <th className="px-2 py-2">Diện tích</th>
                <th className="px-2 py-2">Toàn bộ</th>
                <th className="px-2 py-2">Thao tác</th>
            </tr>
            {slots.map((slot, i) => (
                <tr key={i} className="border border-1">
                    <td className="text-sm font-semibold px-2 py-2">
                        {slot?.bin?.id}
                    </td>
                    <td className="text-sm font-semibold px-2">
                        {slot?.bin?.product}
                    </td>
                    <td className="text-sm font-semibold px-2">
                        {slot?.bin?.quality_with_type}
                    </td>
                    <td className="text-sm font-semibold px-2">
                        {slot?.bin?.packaged}
                    </td>
                    <td className="text-sm font-semibold px-2">
                        {slot?.in_slot_weight} kg
                    </td>
                    <td className="text-sm font-semibold px-2 w-fit">
                        {slot?.location}
                    </td>
                    <td
                        className={cn(
                            'text-sm font-semibold px-2 w-fit',
                            slot.taken_area && slot.taken_weight
                                ? 'text-sky-800'
                                : 'text-gray-300'
                        )}
                    >
                        {`${slot.taken_area} m2, ${slot.taken_weight} kg`}
                    </td>
                    <td className="w-fit px-2">
                        <input
                            className="outline-none px-3 text-sm  text-black w-[70px] focus:outline-sky-800 rounded-sm"
                            placeholder="... kg"
                            ref={weightRef}
                        />
                    </td>
                    <td className="w-fit px-2">
                        <input
                            className="outline-none px-3 text-sm text-black w-[70px] focus:outline-sky-800 rounded-sm"
                            placeholder="... m2"
                            ref={areaRef}
                        />
                    </td>
                    <td className="w-fit">
                        <div className="flex items-center justify-center">
                            <input type="checkbox" />
                        </div>
                    </td>
                    <td className="w-fit">
                        <div className="flex items-center gap-2 justify-center">
                            <GoMoveToEnd
                                onClick={() =>
                                    loadToExportTicket({
                                        id: i,
                                        weight: Number(
                                            weightRef!.current!.value
                                        ),
                                        area: Number(areaRef!.current!.value)
                                    })
                                }
                                className="cursor-pointer text-sky-800 w-[24px] h-[24px] rounded-full hover:bg-sky-100 p-1"
                            />
                            <IoClose
                                onClick={() =>
                                    unloadFromExportTicket({ id: i })
                                }
                                className="cursor-pointer text-red-500 w-[24px] h-[24px] rounded-full hover:bg-red-100 p-1"
                            />
                        </div>
                    </td>
                </tr>
            ))}
        </table>
    );
};

export default SlotTable;
