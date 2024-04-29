import { FaPenNib, FaRegFileAlt } from 'react-icons/fa';
import { FaUserGear } from 'react-icons/fa6';
import { BiPhoneCall } from 'react-icons/bi';
import { CiLocationOn } from 'react-icons/ci';
import { useLocalStorage } from '@renderer/hooks';
import { CiDollar } from 'react-icons/ci';
import { CiDeliveryTruck } from 'react-icons/ci';

import {
    Button,
    FormInput,
    DataField,
    AsyncSelectInput,
    SelectInput,
    TextAreaInput
} from '@renderer/components';
import { DatePicker } from '@renderer/components';
import { searchPartner } from '@renderer/services/api';
import { ImportFormStep } from './type';
import { useState } from 'react';

type ImportFormFirstStepProps = {
    goToStep: (step: ImportFormStep) => void;
};

enum DebtOption {
    Yes = 'Có',
    No = 'Không'
}

const ImportFormFirstStep = ({ goToStep }: ImportFormFirstStepProps) => {
    const [debt, setDebt] = useState<DebtOption>(DebtOption.No);
    const [importDate, setImportDate] = useState({
        startDate: null,
        endDate: null
    });
    const updateImportDate = (newValue) => {
        console.log('newValue:', newValue);
        setImportDate(newValue);
    };

    const [partner, setPartner] = useState<any>(null);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const searchPartnerCallback = async () => {
        const response = await searchPartner({
            token: accessToken,
            pageIndex: 1
            // pageSize: 20
        });
        console.log('partners: ', response);
        return response?.content;
    };

    return (
        <div className="w-full">
            <div className="text-xl font-semibold mb-2">Phiếu nhập</div>
            <div className="flex items-center justify-center gap-10">
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput
                        label="Tên phiếu"
                        name="Tên phiếu"
                        icon={<FaPenNib />}
                        bg="bg-white"
                    />
                    <FormInput
                        label="Nhà cung cấp"
                        name="Nhà cung cấp"
                        icon={<FaUserGear />}
                        bg="bg-white"
                    />
                    {/* <AsyncSelectInput
                        label="partners"
                        placeHolder="Đối tác"
                        asyncSelectorCallback={searchPartnerCallback}
                        selectedValue={'Chọn đối tác'}
                        onSelect={setPartner}
                    /> */}
                    <DataField
                        name="Liên hệ"
                        icon={<BiPhoneCall />}
                        disabled={true}
                    />
                    <DataField
                        name="Địa chỉ"
                        icon={<CiLocationOn />}
                        disabled={true}
                    />
                    <DatePicker
                        name="Ngày nhập kho"
                        placeHolder="Ngày nhập"
                        onChange={updateImportDate}
                        value={importDate}
                    />
                    {/* <FormInput name="Ngày xuất" icon={<MdOutlineDateRange />} /> */}
                    {/* <FormInput name="Khối lượng" icon={<FaWeightScale />} /> */}
                </div>
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput
                        label="Vận chuyển"
                        name="Vận chuyển"
                        icon={<CiDeliveryTruck />}
                        bg="bg-white"
                    />
                    <FormInput
                        label="Giá trị"
                        name="Giá trị"
                        icon={<CiDollar />}
                        bg="bg-white"
                    />
                    <SelectInput
                        placeHolder="Công nợ"
                        icon={<CiDollar />}
                        selectedValue={debt}
                        onSelect={setDebt}
                        values={[DebtOption.Yes, DebtOption.No]}
                    />
                    {debt === DebtOption.Yes && (
                        <>
                            <FormInput
                                label="Tiền nợ"
                                name="Tiền nợ"
                                icon={<CiDollar />}
                                bg="bg-white"
                            />
                            <DatePicker
                                name="Ngày đáo hạn"
                                placeHolder="Đáo hạn"
                                onChange={updateImportDate}
                                value={importDate}
                            />
                            <TextAreaInput
                                label="Mô tả"
                                name="Mô tả"
                                icon={<FaRegFileAlt />}
                                bg="bg-white"
                            />
                        </>
                    )}
                </div>
            </div>
            <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                <Button
                    className="text-[#008767] border-[#008767] bg-[#16C098]"
                    text="Tiếp theo"
                    action={() => goToStep(ImportFormStep.Second)}
                />
            </div>
        </div>
    );
};

export default ImportFormFirstStep;
