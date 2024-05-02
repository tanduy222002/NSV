import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useState, useRef } from 'react';
import { CgCloseR } from 'react-icons/cg';
import { MapViewMode } from '../warehouse/components/WarehouseMapPreview';
import { useModal } from '@renderer/hooks';
import { Button } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { WarehouseMapPreview } from '../warehouse/components';
import { getWarehouseDetail } from '@renderer/services/api';
import { BinWithSlot } from '@renderer/types/import';
import SelectSlotList from './SelectSlotList';
import SlotPicker from './SlotPicker';

type SelectSlotPopupProps = {
    warehouseId: number;
    addNewBatch: (param: BinWithSlot[]) => void;
};

const SelectSlotPopup = ({
    warehouseId,
    addNewBatch
}: SelectSlotPopupProps) => {
    const queryClient = useQueryClient();
    const dataLoadRef = useRef<HTMLInputElement>(null);
    const weightRef = useRef<HTMLInputElement>(null);

    const handleConfirm = () => {
        const slots: BinWithSlot[] = selectedSlots.map((slot) => ({
            weight: slot.weight,
            slot_id: slot.id,
            area: slot.usedLoad
        }));
        addNewBatch(slots);
        closeModal && closeModal();
    };

    const { getItem } = useLocalStorage('access-token');
    const [{ rowIndex, slotIndex }, setCurrentSlotIndex] = useState<any>({
        rowIndex: null,
        slotIndex: null
    });

    const [selectedSlots, setSelectedSlots] = useState<any[]>([]);

    const accessToken = getItem();
    const { data, isFetching } = useQuery({
        queryKey: ['warehouse', warehouseId],
        queryFn: () =>
            getWarehouseDetail({
                token: accessToken,
                warehouseId: warehouseId
            })
    });

    const currentSlot = data?.rows[rowIndex]?.slots[slotIndex];

    const updateCurrentSlot = () => {
        const weight = Number(weightRef?.current?.value);
        const load = Number(dataLoadRef?.current?.value);
        const availableLoad = currentSlot?.capacity - currentSlot?.currentLoad;
        if (load <= availableLoad) {
            // update current slot load
            const newData = { ...data };
            const updatedLoad = currentSlot.currentLoad + load;
            newData!.rows[rowIndex]!.slots[slotIndex] = {
                ...currentSlot,
                currentLoad: updatedLoad
            };
            queryClient.setQueryData(['warehouse', warehouseId], newData);
            // add slot to list
            setSelectedSlots((prev) => [
                {
                    id: currentSlot?.id,
                    rowIndex,
                    slotIndex,
                    usedLoad: load,
                    name: currentSlot?.name,
                    weight
                },
                ...prev
            ]);
        }

        // // reset input value
        if (dataLoadRef!.current) dataLoadRef!.current!.value = '';
        if (weightRef!.current) weightRef!.current!.value = '';
    };

    const removeSelectedSlot = (selectedSlot) => {
        // unload selected slot from warehouse map
        const newData = { ...data };
        const updatedLoad = currentSlot.currentLoad - selectedSlot.usedLoad;
        newData!.rows[rowIndex]!.slots[slotIndex] = {
            ...currentSlot,
            currentLoad: updatedLoad
        };
        // remove selected slot from list
        queryClient.setQueryData(['warehouse', warehouseId], newData);
        setSelectedSlots((prev) =>
            prev.filter(
                (slot) =>
                    slot.rowIndex !== selectedSlot.rowIndex ||
                    slot.slotIndex !== selectedSlot.slotIndex
            )
        );
    };

    const { closeModal } = useModal();
    return (
        <div className="shadow fixed flex flex-col items-center gap-2 px-5 py-5 z-50 bg-white border border-gray-100 rounded-md h-[600px] w-[800px] top-[100px] left-[450px]">
            <CgCloseR
                className="ml-auto cursor-pointer hover:text-red-500"
                onClick={closeModal}
            />
            <h1 className="text-xl font-semibold mb-5">Chọn khu vực chứa</h1>
            <div className="flex gap-20">
                {isFetching ? (
                    <h1>Loading map...</h1>
                ) : (
                    <WarehouseMapPreview
                        warehouseMap={data}
                        viewMode={MapViewMode.Select}
                        selectAction={setCurrentSlotIndex}
                    />
                )}
                <div className="w-full max-w-[500px]">
                    <SlotPicker
                        dataLoadRef={dataLoadRef}
                        weightRef={weightRef}
                        currentSlot={currentSlot}
                        updateCurrentSlot={updateCurrentSlot}
                    />
                    {selectedSlots.length > 0 && (
                        <SelectSlotList
                            selectedSlots={selectedSlots}
                            removeSelectedSlot={removeSelectedSlot}
                        />
                    )}
                </div>
            </div>
            <Button
                className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-700 hover:bg-sky-100 font-semibold w-fit"
                text="Xác nhận"
                action={handleConfirm}
            />
        </div>
    );
};

export default SelectSlotPopup;
