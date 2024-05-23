import { useRef, useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import { BiCategoryAlt } from 'react-icons/bi';
import { FaBraille, FaMapLocationDot } from 'react-icons/fa6';
import { MdWarehouse, MdLabelImportantOutline } from 'react-icons/md';
import { useLocalStorage } from '@renderer/hooks';
import { FormSection } from './components';
import { createWarehouse } from '@renderer/services/api';
import {
    AddressPickerController,
    ModalProvider,
    AsyncSelectInput,
    PageLoading,
    ConfirmationPopup,
    Loading
} from '@renderer/components';
import { WarehouseMapPreview } from './components';
import { Button, FormInput, InformationPopup } from '@renderer/components';
import { getWarehouseMap, getWarehouseCategory } from '@renderer/services/api';
import { getUnusedMap } from '@renderer/services/api/warehouse/getUnusedMap';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import {
    CreateWarehouseResult,
    createWarehouseConfirmPopupData
} from '@renderer/constants/warehouse';

type SelectOption = {
    id: number;
    name: string;
};

const CreateWarehouseForm = () => {
    const [confirmPopup, setConfirmPopup] = useState<InfoPopup | null>(null);
    const closeConfirmPopup = () => setConfirmPopup(null);
    const openConfirmPopup = () =>
        setConfirmPopup(createWarehouseConfirmPopupData);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

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

    const createWarehouseMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await createWarehouse(payload);
            return response;
        }
    });

    const hanldeCreateWarehouse = async () => {
        const response: any = await createWarehouseMutation.mutateAsync({
            token: accessToken,
            body: {
                name: nameRef.current!.value,
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
        closeConfirmPopup();
        if (response?.status === 200) {
            setResultPopup(CreateWarehouseResult.Success);
            return;
        }
        if (response?.status >= 400) {
            setResultPopup(CreateWarehouseResult.Error);
            return;
        }
    };

    return (
        <form className="w-full max-w-[900px] relative flex flex-col justify-center bg-white py-5 px-5">
            {createWarehouseMutation.isPending && <Loading />}
            {confirmPopup && (
                <ConfirmationPopup
                    title={confirmPopup.title}
                    body={confirmPopup.body}
                    confirmAction={hanldeCreateWarehouse}
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
                {isFetching ? (
                    <PageLoading />
                ) : (
                    data && <WarehouseMapPreview warehouseMap={data} />
                )}
            </FormSection>
            <Button
                action={openConfirmPopup}
                text="Lưu kho"
                className="text-sky-800 border-sky-800 hover:text-white hover:bg-sky-800 mx-auto mt-6"
            />
        </form>
    );
};

export default CreateWarehouseForm;
