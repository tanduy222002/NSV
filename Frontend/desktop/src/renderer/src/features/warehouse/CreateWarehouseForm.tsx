import { useRef, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { BiCategoryAlt } from 'react-icons/bi';
import { FaRegEdit } from 'react-icons/fa';
import { FaBraille, FaMapLocationDot } from 'react-icons/fa6';
import { MdWarehouse, MdLabelImportantOutline } from 'react-icons/md';
import { useLocalStorage } from '@renderer/hooks';
import { FormSection } from './components';
import { createWarehouse } from '@renderer/services/api';
import {
    AddressPickerController,
    ModalProvider,
    AsyncSelectInput,
    PageLoading
} from '@renderer/components';
import { WarehouseMapPreview } from './components';
import { Button, FormInput, InformationPopup } from '@renderer/components';
import { getWarehouseMap, getWarehouseCategory } from '@renderer/services/api';
import { getUnusedMap } from '@renderer/services/api/warehouse/getUnusedMap';

type SelectOption = {
    id: number;
    name: string;
};

const CreateWarehouseForm = () => {
    const [popupInfo, setPopupInfo] = useState<any>(null);
    const closePopup = () => setPopupInfo(null);

    const [address, setAddress] = useState<any | null>(null);
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

    const searchUnusedWarehouseMapCallback = async () => {
        return await getUnusedMap({ token: accessToken });
    };

    const hanldeSubmit = async (e) => {
        e.preventDefault();
        console.log('form submitted');

        console.log('address: ', address);
        console.log('name: ', nameRef.current?.value);
        const warehouseName = nameRef.current!.value;
        console.log('type: ', warehouseCategory);
        const response = await createWarehouse({
            token: accessToken,
            body: {
                name: warehouseName,
                type: warehouseCategory,
                mapId: warehouseMap?.id,
                address: {
                    address: address?.address,
                    wardId: address?.ward?.id,
                    districtId: address?.district?.id,
                    provinceId: address?.province?.id
                }
            }
        });

        console.log('response: ', response);
        if (response) {
            setPopupInfo({
                title: 'Thành công',
                body: 'Kho mới đã được tạo',
                type: 'success'
            });
            alert('Tạo kho mới thành công');
        }
    };

    return (
        <form
            className="w-full max-w-[900px] relative flex flex-col justify-center bg-white py-5 px-5"
            onSubmit={hanldeSubmit}
        >
            {popupInfo && (
                <InformationPopup {...popupInfo} closeAction={closePopup} />
            )}
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
                            address={address}
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
                        asyncSelectorCallback={searchUnusedWarehouseMapCallback}
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
                {isFetching ? (
                    <PageLoading />
                ) : (
                    data && <WarehouseMapPreview warehouseMap={data} />
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
