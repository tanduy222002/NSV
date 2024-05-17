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
import { ExportFormStep } from '@renderer/types/export';
import { DebtOption } from '@renderer/types/import';
import { ExportTicket } from '@renderer/types/export';

type ExportFormFirstStepProps = {
    exportTicket: ExportTicket;
    updateExportTicket: (value: ExportTicket) => void;
    goToStep: (step: ExportFormStep) => void;
};

const ExportFormFirstStep = ({
    exportTicket,
    updateExportTicket,
    goToStep
}: ExportFormFirstStepProps) => {
    const ticketNameRef = useRef<HTMLInputElement>(null);
    const transportRef = useRef<HTMLInputElement>(null);
    // const costRef = useRef<HTMLInputElement>(null);
    const debtRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);
    const [debtOption, setDebtOption] = useState<DebtOption>(DebtOption.No);
    const [partner, setPartner] = useState<any>(exportTicket?.customer_detail);
    const [exportDate, setExportDate] = useState({
        startDate: exportTicket?.export_date,
        endDate: exportTicket?.export_date
    });
    const updateExportDate = (newValue) => {
        console.log('new date value:', newValue);
        setExportDate(newValue);
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
        const newExportTicketValue: ExportTicket = {
            ...exportTicket,
            name: ticketNameRef?.current?.value ?? '',
            customer_id: partner?.id,
            transporter: transportRef?.current?.value ?? '',
            description: descriptionRef?.current?.value ?? '',
            export_date: exportDate?.startDate ?? String(Date.now())
            // value: costRef?.current ? Number(costRef?.current?.value) : 0
        };
        if (debtOption === DebtOption.Yes) {
            newExportTicketValue.debt = {
                name: '',
                value: debtRef?.current?.value
                    ? Number(debtRef?.current?.value)
                    : 0,
                description: descriptionRef?.current?.value ?? '',
                unit: 'VND',
                due_date: dueDate.startDate ?? String(Date.now())
            };
        }

        console.log('form 1st step value: ', newExportTicketValue);
        updateExportTicket(newExportTicketValue);
        goToStep(ExportFormStep.Second);
    };

    console.log('export ticket: ', exportTicket);

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
                        defaultValue={exportTicket?.name}
                    />
                    <AsyncSelectInput
                        label="partners"
                        placeHolder="Đối tác"
                        icon={<FaUserGear className="text-sky-800" />}
                        asyncSelectorCallback={searchPartnerCallback}
                        selectedValue={
                            partner?.name ?? exportTicket?.customer_detail?.name
                        }
                        onSelect={setPartner}
                    />
                    <DataField
                        name="Liên hệ"
                        value={
                            partner?.phone ??
                            exportTicket?.customer_detail?.phone
                        }
                        defaultValue="Liên hệ"
                        icon={<BiPhoneCall />}
                        disabled={!exportTicket?.customer_id}
                    />
                    <DataField
                        name="Địa chỉ"
                        defaultValue="Địa chỉ"
                        icon={<CiLocationOn />}
                        disabled={!exportTicket?.customer_id}
                        value={
                            partner?.address ??
                            exportTicket?.customer_detail?.address
                        }
                    />
                    <DatePicker
                        name="Ngày xuất kho"
                        placeHolder="Ngày xuất"
                        onChange={updateExportDate}
                        value={exportDate}
                    />
                </div>
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput
                        label="Vận chuyển"
                        name="Vận chuyển"
                        icon={<CiDeliveryTruck />}
                        bg="bg-white"
                        ref={transportRef}
                        defaultValue={exportTicket?.transporter}
                    />
                    {/* <FormInput
                        label="Giá trị"
                        name="Giá trị"
                        icon={<CiDollar />}
                        bg="bg-white"
                        ref={costRef}
                        defaultValue={exportTicket?.value}
                    /> */}
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
                    className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                    text="Tiếp theo"
                    action={handleFirstStep}
                />
            </div>
        </div>
    );
};

export default ExportFormFirstStep;
