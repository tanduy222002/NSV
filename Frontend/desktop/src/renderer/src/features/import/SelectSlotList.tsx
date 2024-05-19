import { CgCloseR } from 'react-icons/cg';

type SelectSlotListProps = {
    selectedSlots: any[];
    removeSelectedSlot: (param: any) => void;
};

const SelectSlotList = ({
    selectedSlots,
    removeSelectedSlot
}: SelectSlotListProps) => {
    return (
        <div className="h-full">
            <h2 className="font-semibold text-lg text-sky-800">
                Các vị trí kho đã chọn
            </h2>
            <div className="flex flex-col gap-3 px-3 py-3 max-h-[200px] overflow-y-scroll">
                {selectedSlots?.map((slot, index) => (
                    <div
                        className="border border-sky-800 text-sky-800 rounded-md relative px-3 py-3"
                        key={index}
                    >
                        <CgCloseR
                            className="absolute top-3 right-3 hover:text-red-500 cursor-pointer"
                            onClick={() => removeSelectedSlot(slot)}
                        />
                        <p className="text-sm font-semibold">
                            Vị trí: {slot.name}
                        </p>
                        <p className="text-sm font-semibold">
                            Sức chứa sử dụng: {slot.usedLoad}
                        </p>
                        <p className="text-sm font-semibold">
                            Khối lượng: {slot.weight}
                        </p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SelectSlotList;
