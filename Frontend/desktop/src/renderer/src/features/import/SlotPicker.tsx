import { FormInput, Button, DataField } from '@renderer/components';

type SlotPickerProps = {
    dataLoadRef: any;
    weightRef: any;
    currentSlot: any;
    updateCurrentSlot: () => void;
};

const SlotPicker = ({
    currentSlot,
    updateCurrentSlot,
    dataLoadRef,
    weightRef
}: SlotPickerProps) => {
    return (
        <div className="flex flex-col gap-3 mb-5">
            <h2 className="font-semibold">Vị trí kho: </h2>
            <DataField
                name="Sức chứa tối đa:"
                defaultValue={'...'}
                disabled={!currentSlot?.capacity}
                value={currentSlot?.capacity}
            />
            <DataField
                name="Sức chứa khả dụng:"
                defaultValue={'...'}
                disabled={!(currentSlot?.capacity - currentSlot?.currentLoad)}
                value={currentSlot?.capacity - currentSlot?.currentLoad}
            />
            <FormInput label="Diện tích muốn sử dụng" ref={dataLoadRef} />
            <FormInput label="Khối lượng lô hàng" ref={weightRef} />
            <Button
                className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-700 hover:bg-sky-100 font-semibold w-fit"
                text="Thêm mới"
                action={() => updateCurrentSlot()}
            />
        </div>
    );
};

export default SlotPicker;
