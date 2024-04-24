import { useRef, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { BiCategoryAlt } from 'react-icons/bi';
import { FaRegEdit } from 'react-icons/fa';
import { MdWarehouse, MdLabelImportantOutline } from 'react-icons/md';
import { useLocalStorage } from '@renderer/hooks';
import { FormSection } from './components';
import {
    AddressPickerController,
    ModalProvider,
    AsyncSelectInput
} from '@renderer/components';
import { WarehouseMapPreview } from './components';
import { FaBraille, FaMapLocationDot } from 'react-icons/fa6';
import { Button, FormInput } from '@renderer/components';
import {
    searchWarehouseMap,
    getWarehouseMap,
    getWarehouseCategory
} from '@renderer/services/api';

type SelectOption = {
    id: number;
    name: string;
};

const CreateWarehouseForm = () => {
    const [address, setAddress] = useState<object | null>(null);
    const updateAddress = (address: any) => setAddress(address);
    const [warehouseCategory, setWarehouseCategory] = useState('Chọn loại kho');
    const [warehouseMap, setWarehouseMap] = useState<SelectOption>({
        id: 0,
        name: 'Chọn sơ đồ kho'
    });
    const nameRef = useRef<HTMLInputElement>(null);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['warehouse-map', warehouseMap.id],
        queryFn: async () => {
            return await getWarehouseMap({
                token: accessToken,
                mapId: warehouseMap.id
            });
        }
    });

    const getWarehouseCategoryCallback = async () => {
        const result = await getWarehouseCategory({ token: accessToken });
        return result;
    };

    const searchWarehouseMapCallback = async () => {
        const result = await searchWarehouseMap({ token: accessToken });
        return result;
    };

    const hanldeSubmit = (e) => {
        e.preventDefault();
        console.log('form submitted');

        console.log('address: ', address);
        console.log('name: ', nameRef.current?.value);
        console.log('type: ', warehouseCategory);
    };

    return (
        <form
            className="w-full max-w-[900px] h-full relative flex flex-col justify-center bg-white py-5 px-5"
            onSubmit={hanldeSubmit}
        >
            <FormSection
                title="Thông tin kho"
                icon={<FaBraille />}
                layoutClassName="flex flex-col gap-2"
            >
                <div className="flex flex-col items-center gap-4">
                    <FormInput
                        label="Tên kho"
                        ref={nameRef}
                        icon={
                            <MdLabelImportantOutline className="text-sky-800" />
                        }
                        bg="bg-white"
                    />
                    <ModalProvider>
                        <AddressPickerController
                            updateAddress={updateAddress}
                        />
                    </ModalProvider>

                    <AsyncSelectInput
                        icon={<BiCategoryAlt />}
                        label="warehouse-category"
                        selectedValue={warehouseCategory}
                        placeHolder="Chọn loại kho"
                        asyncSelectorCallback={getWarehouseCategoryCallback}
                        onSelect={setWarehouseCategory}
                    />

                    <AsyncSelectInput
                        icon={<FaMapLocationDot />}
                        label="warehouse-map"
                        selectedValue={warehouseMap?.name}
                        placeHolder="Chọn sơ đồ"
                        asyncSelectorCallback={searchWarehouseMapCallback}
                        onSelect={setWarehouseMap}
                    />
                </div>
            </FormSection>
            <FormSection
                title="Xem trước"
                icon={<MdWarehouse />}
                layoutClassName="flex flex-col items-center relative"
            >
                <FaRegEdit className="absolute -top-7 left-[110px] text-[#F78F1E] cursor-pointer" />
                {isFetching || !data ? (
                    <h1>Loading...</h1>
                ) : (
                    <WarehouseMapPreview warehouseMap={data} />
                )}
            </FormSection>
            <Button
                type="submit"
                text="Lưu kho"
                className="text-[#1A3389] border-[#1A3389] mx-auto mt-6"
            />
        </form>
    );
};

export default CreateWarehouseForm;
