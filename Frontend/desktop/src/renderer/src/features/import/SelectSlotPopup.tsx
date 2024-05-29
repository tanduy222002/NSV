import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useState, useRef } from 'react';
import { CgCloseR } from 'react-icons/cg';
import { MapViewMode } from '../warehouse/components/WarehouseMapPreview';
import { useModal } from '@renderer/hooks';
import { Button, PageLoading } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { WarehouseMapPreview } from '../warehouse/components';
import { getWarehouseDetail } from '@renderer/services/api';
import { BinWithSlot } from '@renderer/types/import';
import SelectSlotList from './SelectSlotList';
import SlotPicker from './SlotPicker';
import { cn } from '@renderer/utils/util';

type SelectSlotPopupProps = {
    totalWeight: number;
    warehouseId: number;
    addNewBatch: (param: BinWithSlot[]) => void;
};

const SelectSlotPopup = ({
    totalWeight,
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

    const currentSlot = data?.map?.rows?.[rowIndex]?.slots?.[slotIndex] ?? null;

    const updateCurrentSlot = () => {
        const weight = Number(weightRef?.current?.value);
        const load = Number(dataLoadRef?.current?.value);
        const availableLoad = currentSlot?.capacity - currentSlot?.currentLoad;
        if (load <= availableLoad) {
            // update current slot load
            const newData = { ...data };
            const updatedLoad = currentSlot.currentLoad + load;
            newData!.map!.rows[rowIndex]!.slots[slotIndex] = {
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
        newData!.map!.rows[rowIndex]!.slots[slotIndex] = {
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

    const selectedWeight = selectedSlots.reduce(
        (totalWeight, slot) => totalWeight + slot.weight,
        0
    );

    return (
        <div className="shadow-md fixed flex flex-col items-center gap-2 p-5 z-50 bg-white border border-gray-100 rounded-md h-full max-h-[700px] w-fit min-w-[1100px] top-1/2 -translate-y-1/2 left-1/2 -translate-x-1/2">
            <div className="ml-auto cursor-pointer w-fit">
                <CgCloseR
                    className="ml-auto cursor-pointer hover:text-red-500 w-[16px] h-[16px]"
                    onClick={closeModal}
                />
            </div>
            <h1 className="text-xl font-semibold mb-5">Chọn khu vực chứa</h1>
            <div className="mr-auto font-semibold">
                <p className="font-semibold text-lg text-sky-800">
                    Cần chọn: {totalWeight} kg
                </p>
                <p
                    className={
                        (cn('text-lg'),
                        totalWeight !== selectedWeight
                            ? 'text-red-500'
                            : 'text-emerald-500')
                    }
                >
                    Đã chọn: {selectedWeight} kg
                </p>
            </div>
            <div className="flex gap-20 w-full">
                {isFetching ? (
                    <PageLoading />
                ) : (
                    <div className="space-y-5 w-fit min-w-[300px]">
                        <WarehouseMapPreview
                            warehouseMap={data?.map}
                            viewMode={MapViewMode.Select}
                            selectAction={setCurrentSlotIndex}
                        />
                        <SlotPicker
                            dataLoadRef={dataLoadRef}
                            weightRef={weightRef}
                            currentSlot={currentSlot}
                            updateCurrentSlot={updateCurrentSlot}
                        />
                    </div>
                )}
                <div className="w-full max-w-[500px]">
                    {selectedSlots.length > 0 && (
                        <SelectSlotList
                            selectedSlots={selectedSlots}
                            removeSelectedSlot={removeSelectedSlot}
                        />
                    )}
                </div>
            </div>
            <Button
                className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-800 hover:bg-sky-100 font-semibold w-fit"
                text="Xác nhận"
                action={handleConfirm}
            />
        </div>
    );
};

export default SelectSlotPopup;
