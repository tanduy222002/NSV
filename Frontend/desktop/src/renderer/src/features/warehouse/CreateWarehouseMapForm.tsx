import { useRef } from 'react';
import { Formik, FieldArray } from 'formik';
import { FormSection, WarehouseMapPreview } from './components';
import { MapRow, WarehouseMap as CreateWarehouseMapFormValues } from './type';
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
    const nameRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);
    const slotRef = useRef<HTMLInputElement>(null);

    const { getItem } = useLocalStorage('access-token');
    const token = getItem();

    const addMapRow = (pushCallback: any) => {
        console.log(
            'name: ',
            nameRef?.current?.value,
            'slot: ',
            slotRef?.current?.value,
            'capacity: ',
            areaRef.current?.value
        );

        const slot = parseInt(slotRef!.current!.value);
        const name = nameRef?.current?.value;
        const capacity = parseInt(areaRef.current!.value);

        const newRow = Array.from(Array(slot).keys())
            .map((slotId) => slotId + 1)
            .map((slotId) => ({
                capacity: capacity,
                description: '',
                name: `#${name}${slotId}`
            }));

        const row: MapRow = {
            name: name as string,
            slots: [...newRow]
        };
        pushCallback(row);
    };

    const validateInput = ({ name, rows }: CreateWarehouseMapFormValues) => {
        const errors: any = {};
        console.log('name: ', name, 'rows: ', rows);
        // if (!username) {
        //     errors.username = 'Required';
        // }
        // if (!password) {
        //     errors.password = 'Required';
        // }
        console.log('validation error: ', errors);

        return errors;
    };

    const handleCreateLayoutSubmit = async (
        values: CreateWarehouseMapFormValues
    ) => {
        console.log('submit value: ', values);
        const response: any = await createWarehouseMap({
            token: token,
            warehouseMap: values
        });
        console.log('create response: ', response);
        if (response?.status === 200) {
            alert('Tạo sơ đồ kho thành công');
        }
    };

    return (
        <Formik
            initialValues={createWareHouseMapInitValues}
            validate={validateInput}
            onSubmit={(values) => handleCreateLayoutSubmit(values)}
        >
            {({ values, handleChange, handleSubmit }) => (
                <form
                    className="w-4/5 relative flex flex-col justify-center rounded-md bg-white py-5 px-8"
                    onSubmit={handleSubmit}
                >
                    <FormInput
                        name="name"
                        value={values.name}
                        label="Tên sơ đồ"
                        ref={slotRef}
                        onChange={handleChange}
                    />
                    <FieldArray name="rows">
                        {({ push, remove }) => (
                            <>
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
                                        />
                                        <FormInput
                                            label="Diện tích(m²)"
                                            name="Diện tích(m²)"
                                            ref={areaRef}
                                        />
                                        <FormInput
                                            label="Tên lô"
                                            name="Tên lô"
                                            ref={nameRef}
                                        />
                                    </div>
                                    <Button
                                        text="Thêm mới"
                                        className="text-[#1A3389] border-[#1A3389]"
                                        action={() => addMapRow(push)}
                                    />
                                </FormSection>
                                {values.rows.length > 0 && (
                                    <FormSection
                                        title="Xem trước"
                                        icon={<MdWarehouse />}
                                        layoutClassName="flex flex-col items-center"
                                    >
                                        <WarehouseMapPreview
                                            warehouseMap={values}
                                            viewMode={MapViewMode.Edit}
                                            deleteRow={remove}
                                        />
                                    </FormSection>
                                )}
                            </>
                        )}
                    </FieldArray>
                    <Button
                        text="Lưu sơ đồ"
                        type="submit"
                        className="text-[#1A3389] border-[#1A3389] mx-auto"
                    />
                </form>
            )}
        </Formik>
    );
};

export default CreateWarehouseMapForm;
