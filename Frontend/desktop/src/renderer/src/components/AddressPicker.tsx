import { useState, useRef } from 'react';
import { CgCloseR } from 'react-icons/cg';
import { useModal, useLocalStorage } from '@renderer/hooks';
import { getProvinces, getDistricts, getWards } from '@renderer/services/api';
import { FormInput, Button } from '@renderer/components';
import AsyncSelectInput from './AsyncSelectInput';
import { Address } from '@renderer/types/partner';

type Location = {
    id: number;
    name: string;
};

type AddressPickerProps = {
    updateAddressDetail: (address: Address) => void;
};

const AddressPicker = ({ updateAddressDetail }: AddressPickerProps) => {
    const [province, setProvince] = useState<Location | null>(null);
    const updateProvince = (province: Location) => setProvince(province);
    const [district, setDistrict] = useState<Location | null>(null);
    const updateDistrict = (district: Location) => setDistrict(district);
    const [ward, setWard] = useState<Location | null>(null);
    const updateWard = (ward: Location) => setWard(ward);

    const addressDetailRef = useRef<HTMLInputElement>(null);
    const { closeModal } = useModal();

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const getProvincesCallback = async () => {
        return await getProvinces({ token: accessToken });
    };
    const getDistrictsCallback = async () => {
        return (
            province?.id &&
            (await getDistricts({
                token: accessToken,
                provinceId: province?.id
            }))
        );
    };
    const getWardsCallback = async () => {
        return (
            province?.id &&
            district?.id &&
            (await getWards({
                token: accessToken,
                provinceId: province?.id,
                districtId: district?.id
            }))
        );
    };

    const saveAddress = () => {
        console.log({
            province: province,
            district: district,
            ward: ward,
            address: addressDetailRef?.current?.value
        });

        updateAddressDetail({
            province: province,
            district: district,
            ward: ward,
            address: addressDetailRef?.current?.value as string
        });
        closeModal && closeModal();
    };

    return (
        <div className="absolute top-[40px] left-1/2 -translate-x-1/2 z-50">
            <div className="relative px-5 py-5 flex flex-col gap-5 border border-gray-400 bg-gray-50 rounded-md">
                <CgCloseR
                    className="absolute top-5 right-5 hover:text-red-500 cursor-pointer"
                    onClick={closeModal}
                />
                <h1 className="text-xl font-semibold">Chọn địa chỉ</h1>
                <AsyncSelectInput
                    selectedValue={province?.name}
                    placeHolder="Chọn tỉnh..."
                    asyncSelectorCallback={getProvincesCallback}
                    label="Tỉnh"
                    onSelect={updateProvince}
                    bg="bg-gray-50"
                />
                <AsyncSelectInput
                    selectedValue={district?.name}
                    placeHolder="Chọn Huyện..."
                    asyncSelectorCallback={getDistrictsCallback}
                    label="Huyện"
                    onSelect={updateDistrict}
                    bg="bg-gray-50"
                />
                <AsyncSelectInput
                    selectedValue={ward?.name}
                    placeHolder="Chọn ..."
                    asyncSelectorCallback={getWardsCallback}
                    label="Phường/xã"
                    onSelect={updateWard}
                    bg="bg-gray-50"
                />
                <FormInput
                    label="Địa chỉ"
                    ref={addressDetailRef}
                    bg="bg-gray-50"
                />
                <Button
                    className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-700 hover:bg-sky-100 font-semibold w-fit"
                    text="Xác nhận"
                    action={saveAddress}
                />
            </div>
        </div>
    );
};

export default AddressPicker;
