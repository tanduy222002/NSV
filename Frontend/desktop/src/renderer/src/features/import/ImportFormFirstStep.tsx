import { useState, useRef } from 'react';
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
import { ImportTicket } from '@renderer/types/import';
import { DebtOption } from '@renderer/types/import';

type ImportFormFirstStepProps = {
    importTicket: ImportTicket;
    updateImportTicket: (value: ImportTicket) => void;
    goToStep: (step: ImportFormStep) => void;
};

const ImportFormFirstStep = ({
    importTicket,
    updateImportTicket,
    goToStep
}: ImportFormFirstStepProps) => {
    const ticketNameRef = useRef<HTMLInputElement>(null);
    const transportRef = useRef<HTMLInputElement>(null);
    const costRef = useRef<HTMLInputElement>(null);
    const debtRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);
    const [debtOption, setDebtOption] = useState<DebtOption>(DebtOption.No);
    const [partner, setPartner] = useState<any>(importTicket?.provider_id);
    const [importDate, setImportDate] = useState({
        startDate: importTicket?.import_date,
        endDate: importTicket?.import_date
    });
    const updateImportDate = (newValue) => {
        console.log('new date value:', newValue);
        setImportDate(newValue);
    };
    const [dueDate, setDueDate] = useState({
        startDate: null,
        endDate: null
    });
    const updateDueDate = (newValue) => {
        console.log('new date value:', newValue);
        setDueDate(newValue);
    };

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    console.log('import ticket: ', importTicket);

    const searchPartnerCallback = async () => {
        const response = await searchPartner({
            token: accessToken,
            pageIndex: 1
            // pageSize: 20
        });
        console.log('partners: ', response);
        return response?.content;
    };

    const handleFirstStep = () => {
        const newImportTicketValue: ImportTicket = {
            ...importTicket,
            name: ticketNameRef?.current?.value ?? '',
            provider_id: partner?.id,
            transporter: transportRef?.current?.value ?? '',
            description: descriptionRef?.current?.value ?? '',
            import_date: importDate?.startDate ?? String(Date.now()),
            provider_detail: { ...partner },
            value: costRef?.current ? Number(costRef?.current?.value) : 0
        };
        if (debtOption === DebtOption.Yes) {
            newImportTicketValue.debtDto = {
                name: '',
                value: debtRef?.current?.value
                    ? Number(debtRef?.current?.value)
                    : 0,
                description: descriptionRef?.current?.value ?? '',
                unit: 'VND',
                due_date: dueDate.startDate ?? String(Date.now())
            };
        }

        console.log('form 1st step value: ', newImportTicketValue);
        updateImportTicket(newImportTicketValue);
        goToStep(ImportFormStep.Second);
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
                        ref={ticketNameRef}
                        defaultValue={importTicket?.name}
                    />
                    <AsyncSelectInput
                        label="partners"
                        placeHolder="Đối tác"
                        icon={<FaUserGear />}
                        asyncSelectorCallback={searchPartnerCallback}
                        selectedValue={
                            partner?.name ?? importTicket?.provider_detail?.name
                        }
                        onSelect={setPartner}
                    />
                    <DataField
                        name="Liên hệ"
                        value={
                            partner?.phone ??
                            importTicket?.provider_detail?.phone
                        }
                        defaultValue="Liên hệ"
                        icon={<BiPhoneCall />}
                        disabled={!importTicket?.provider_id}
                    />
                    <DataField
                        name="Địa chỉ"
                        defaultValue="Địa chỉ"
                        icon={<CiLocationOn />}
                        disabled={!importTicket?.provider_id}
                        value={
                            partner?.address ??
                            importTicket?.provider_detail?.address
                        }
                    />
                    <DatePicker
                        name="Ngày nhập kho"
                        placeHolder="Ngày nhập"
                        onChange={updateImportDate}
                        value={importDate}
                    />
                </div>
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput
                        label="Vận chuyển"
                        name="Vận chuyển"
                        icon={<CiDeliveryTruck />}
                        bg="bg-white"
                        ref={transportRef}
                        defaultValue={importTicket?.transporter}
                    />
                    <FormInput
                        label="Giá trị"
                        name="Giá trị"
                        icon={<CiDollar />}
                        bg="bg-white"
                        ref={costRef}
                        defaultValue={importTicket?.value}
                    />
                    <SelectInput
                        placeHolder="Công nợ"
                        icon={<CiDollar />}
                        selectedValue={debtOption}
                        onSelect={setDebtOption}
                        values={[DebtOption.Yes, DebtOption.No]}
                    />
                    {debtOption === DebtOption.Yes && (
                        <>
                            <FormInput
                                label="Tiền nợ"
                                name="Tiền nợ"
                                icon={<CiDollar />}
                                bg="bg-white"
                                ref={debtRef}
                            />
                            <DatePicker
                                name="Ngày đáo hạn"
                                placeHolder="Đáo hạn"
                                onChange={updateDueDate}
                                value={dueDate}
                            />
                            <TextAreaInput
                                label="Mô tả"
                                name="Mô tả"
                                icon={<FaRegFileAlt />}
                                bg="bg-white"
                                ref={descriptionRef}
                            />
                        </>
                    )}
                </div>
            </div>
            <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                <Button
                    className="text-[#008767] border-[#008767] bg-[#16C098]"
                    text="Tiếp theo"
                    action={handleFirstStep}
                />
            </div>
        </div>
    );
};

export default ImportFormFirstStep;
