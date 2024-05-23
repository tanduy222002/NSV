import { FaWeightScale } from 'react-icons/fa6';
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
            <h2 className="font-semibold text-sky-800 text-lg">Vị trí kho: </h2>
            <DataField
                name="Sức chứa tối đa:"
                defaultValue={'...'}
                disabled={false}
                value={
                    currentSlot?.capacity
                        ? `${currentSlot?.capacity} m²`
                        : 'Chưa chọn vị trí'
                }
            />
            <DataField
                name="Sức chứa khả dụng:"
                defaultValue={'...'}
                disabled={false}
                value={
                    currentSlot?.capacity || currentSlot?.currentLoad
                        ? `${
                              Number(currentSlot?.capacity) -
                              Number(currentSlot?.currentLoad)
                          }  m²`
                        : 'Chưa chọn vị trí'
                }
            />
            <FormInput
                label="Diện tích muốn sử dụng (m²)"
                ref={dataLoadRef}
                bg="bg-white"
            />
            <FormInput
                label="Khối lượng lô hàng (kg)"
                ref={weightRef}
                bg="bg-white"
                icon={<FaWeightScale />}
            />
            <Button
                className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-800 hover:bg-sky-100 font-semibold w-fit"
                text="Thêm mới"
                action={() => updateCurrentSlot()}
            />
        </div>
    );
};

export default SlotPicker;
