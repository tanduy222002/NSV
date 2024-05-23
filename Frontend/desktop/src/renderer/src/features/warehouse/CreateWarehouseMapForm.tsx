import { useRef, useState } from 'react';
import { FormSection, WarehouseMapPreview } from './components';
import {
    MapRow,
    WarehouseMap as CreateWarehouseMapFormValues,
    MapSlot
} from './type';
import {
    ConfirmationPopup,
    FormInput,
    InformationPopup,
    Loading
} from '@renderer/components';
import { FaBraille } from 'react-icons/fa6';
import { MdWarehouse } from 'react-icons/md';
import { createWarehouseMap } from '@renderer/services/api';
import { Button } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { MapViewMode } from './components/WarehouseMapPreview';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import {
    CreateWarehouseMapResult,
    createWarehouseMapConfirmPopupData
} from '@renderer/constants/warehouse';
import { useMutation } from '@tanstack/react-query';

const createWareHouseMapInitValues: CreateWarehouseMapFormValues = {
    name: '',
    rows: []
};

const CreateWarehouseMapForm = () => {
    const [confirmPopup, setConfirmPopup] = useState<InfoPopup | null>();
    const closeConfirmPopup = () => setConfirmPopup(null);
    const openConfirmPopup = () =>
        setConfirmPopup(createWarehouseMapConfirmPopupData);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const [warehouseMap, setWarehouseMap] = useState(
        createWareHouseMapInitValues
    );

    const nameRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);
    const slotRef = useRef<HTMLInputElement>(null);

    const { getItem } = useLocalStorage('access-token');
    const token = getItem();

    const addMapRow = () => {
        const slot = parseInt(slotRef!.current!.value);
        const name = nameRef?.current?.value;
        const capacity = parseInt(areaRef.current!.value);

        const newRowValue: MapSlot[] = Array.from(Array(slot).keys())
            .map((slotId) => slotId + 1)
            .map((slotId) => ({
                capacity: capacity,
                description: '',
                name: `#${name}${slotId}`,
                status: 'EMPTY',
                curentLoad: 0
            }));

        const newRow: MapRow = {
            name: name as string,
            slots: [...newRowValue]
        };

        setWarehouseMap((prev) => ({ ...prev, rows: [...prev.rows, newRow] }));
    };

    const deleteRow = (rowId) => {
        setWarehouseMap((prev) => ({
            ...prev,
            rows: prev.rows.filter((_, id) => id !== rowId)
        }));
    };

    const updateFormValue = (key: string) => {
        return (value: any) => {
            setWarehouseMap((prev) => ({ ...prev, [key]: value }));
        };
    };

    const createWarehouseMapMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await createWarehouseMap(payload);
            return response;
        }
    });

    const handleCreateLayoutSubmit = async () => {
        const response: any = await createWarehouseMapMutation.mutateAsync({
            token: token,
            warehouseMap: warehouseMap
        });
        closeConfirmPopup();
        if (response?.status >= 400) {
            setResultPopup(CreateWarehouseMapResult.Error);
            return;
        }
        if (response?.status === 200) {
            setResultPopup(CreateWarehouseMapResult.Success);
            return;
        }
    };

    return (
        <form
            className="w-4/5 relative flex flex-col justify-center rounded-md bg-white py-5 px-8"
            onSubmit={handleCreateLayoutSubmit}
        >
            {createWarehouseMapMutation.isPending && <Loading />}
            {confirmPopup && (
                <ConfirmationPopup
                    title={confirmPopup.title}
                    body={confirmPopup.body}
                    confirmAction={handleCreateLayoutSubmit}
                    cancelAction={closeConfirmPopup}
                />
            )}
            {resultPopup && (
                <InformationPopup
                    title={resultPopup.title}
                    body={resultPopup.body}
                    popupType={resultPopup.popupType}
                    closeAction={closeResultPopup}
                />
            )}
            <FormInput
                name="name"
                value={warehouseMap.name}
                label="Tên sơ đồ"
                ref={slotRef}
                onChange={updateFormValue('name')}
                bg="bg-white"
            />
            <FormSection
                title="Tạo dãy kho"
                icon={<FaBraille />}
                layoutClassName="flex flex-col gap-2"
            >
                <div className="flex items-center justify-between flex-wrap gap-4">
                    <FormInput
                        label="Số lô chứa"
                        name="Số lô chứa"
                        ref={slotRef}
                        bg="bg-white"
                    />
                    <FormInput
                        label="Diện tích(m²)"
                        name="Diện tích(m²)"
                        ref={areaRef}
                        bg="bg-white"
                    />
                    <FormInput
                        label="Tên lô"
                        name="Tên lô"
                        ref={nameRef}
                        bg="bg-white"
                    />
                </div>
                <Button
                    text="Thêm mới"
                    className="text-sky-800 border-sky-800 hover:bg-sky-800 hover:text-white"
                    action={() => addMapRow()}
                />
            </FormSection>
            {warehouseMap.rows.length > 0 && (
                <FormSection
                    title="Xem trước"
                    icon={<MdWarehouse />}
                    layoutClassName="flex flex-col items-center"
                >
                    <WarehouseMapPreview
                        warehouseMap={warehouseMap}
                        viewMode={MapViewMode.Edit}
                        deleteRow={deleteRow}
                    />
                </FormSection>
            )}
            <Button
                text="Lưu sơ đồ"
                type="button"
                action={openConfirmPopup}
                className="text-sky-800 border-sky-800 mx-auto hover:text-white hover:bg-sky-800"
            />
        </form>
    );
};

export default CreateWarehouseMapForm;
