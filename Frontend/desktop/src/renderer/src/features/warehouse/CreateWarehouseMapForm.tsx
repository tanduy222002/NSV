import { FormEvent, useRef, useState } from 'react';
import { FormSection, WarehouseMapPreview } from './components';
import {
    MapRow,
    WarehouseMap as CreateWarehouseMapFormValues,
    MapSlot
} from './type';
import { FormInput } from '@renderer/components';
import { FaBraille } from 'react-icons/fa6';
import { MdWarehouse } from 'react-icons/md';
import { createWarehouseMap } from '@renderer/services/api';
import { Button } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { MapViewMode } from './components/WarehouseMapPreview';

const createWareHouseMapInitValues: CreateWarehouseMapFormValues = {
    name: '',
    rows: []
};

const CreateWarehouseMapForm = () => {
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

    const handleCreateLayoutSubmit = async (e: FormEvent) => {
        e.preventDefault();
        const response: any = await createWarehouseMap({
            token: token,
            warehouseMap: warehouseMap
        });
        console.log('create response: ', response);
        if (response?.status === 200) {
            alert('Tạo sơ đồ kho thành công');
        }
    };

    return (
        <form
            className="w-4/5 relative flex flex-col justify-center rounded-md bg-white py-5 px-8"
            onSubmit={handleCreateLayoutSubmit}
        >
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
                    className="text-sky-800 border-sky-800"
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
                type="submit"
                className="text-sky-800 border-sky-800 mx-auto"
            />
        </form>
    );
};

export default CreateWarehouseMapForm;
