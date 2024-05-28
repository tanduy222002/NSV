import { ImportBinWithSlot } from '@renderer/types/export';
import SlotRow from './SlotRow';

type SlotTableProps = {
    slots: ImportBinWithSlot[];
    updateExportBins: (bins: ImportBinWithSlot[]) => void;
};

const SlotTable = ({ slots, updateExportBins }: SlotTableProps) => {
    const loadToExportTicket = ({
        id,
        weight,
        area
    }: {
        id: number;
        weight: number;
        area: number;
    }) => {
        console.log('bin: ', weight, area);
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
                <SlotRow
                    i={i}
                    key={slot?.bin?.id}
                    id={slot?.bin?.id}
                    product={slot?.bin?.product}
                    quality={slot?.bin?.quality_with_type}
                    packaged={slot?.bin?.packaged}
                    location={slot?.location}
                    inSlotWeight={slot?.in_slot_weight}
                    takenArea={slot?.taken_area}
                    takenWeight={slot?.taken_weight}
                    loadToExportTicket={loadToExportTicket}
                    unloadFromExportTicket={unloadFromExportTicket}
                />
            ))}
        </table>
    );
};

export default SlotTable;
