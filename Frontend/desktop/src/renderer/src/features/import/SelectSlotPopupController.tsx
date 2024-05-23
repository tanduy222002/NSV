import SelectSlotPopup from './SelectSlotPopup';
import { BinWithSlot } from '@renderer/types/import';

import { useModal } from '@renderer/hooks';

type SelectSlotPopupControllerProps = {
    addNewBatch: (param: BinWithSlot[]) => void;
    warehouseId: number;
};

const SelectSlotPopupController = ({
    addNewBatch,
    warehouseId
}: SelectSlotPopupControllerProps) => {
    const { modalOpen, openModal } = useModal();
    return (
        <div>
            <button
                type="button"
                onClick={() => warehouseId !== 0 && openModal && openModal()}
            >
                <p className="text-xs hover:underline text-gray-300 hover:text-gray-400 font-semibold ml-5 -translate-y-5">
                    Xem chi tiết
                </p>
            </button>
            {modalOpen && (
                <SelectSlotPopup
                    warehouseId={warehouseId}
                    addNewBatch={addNewBatch}
                />
            )}
        </div>
    );
};

export default SelectSlotPopupController;
