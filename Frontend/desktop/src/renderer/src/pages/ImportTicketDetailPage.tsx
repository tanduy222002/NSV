import { IoChevronBack } from 'react-icons/io5';
import { RiMoneyDollarCircleLine } from 'react-icons/ri';
import { FaRegCalendarTimes } from 'react-icons/fa';
import { FaCircleNotch } from 'react-icons/fa';
import { GoNote } from 'react-icons/go';
import { MdMoneyOff } from 'react-icons/md';
import { useNavigate, useParams } from 'react-router-dom';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { isEqual } from 'lodash';
import {
    UserInfo,
    Button,
    TableView,
    DataField,
    ConfirmationPopup,
    InformationPopup
} from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { ImportTicketDetailMainSection } from '@renderer/features/import';
import { getImportTicketDetail } from '@renderer/services/api';
import { ColumnType } from '@renderer/components/TableView';
import { cn } from '@renderer/utils/util';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import {
    approveTicketPopupInfo,
    approveTicketSuccessPopup
} from '@renderer/constants/import';
import {
    removeTicketDebtPopupData,
    removeTicketDebtSuccessPopupData
} from '@renderer/constants/debt';
import { approveImportTicket, removeTicketDebt } from '@renderer/services/api';

enum TicketDetailSection {
    Batch = 'Batch',
    Debt = 'Debt',
    Description = 'Description'
}

const batchTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên lô hàng',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Sản phẩm',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Loại hàng',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Quy cách',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Vị trí',
        sortable: false,
        type: ColumnType.Text
    }
];

const ImportTicketDetailPage = () => {
    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const openApproveTicketPopup = () => setInfoPopup(approveTicketPopupInfo);
    const closeInfoPopup = () => setInfoPopup(null);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const [viewedSection, setViewedSection] = useState(
        TicketDetailSection.Debt
    );

    const { id } = useParams();
    const navigate = useNavigate();
    const goToImportTicketPage = () => navigate('/import');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { data, isFetching, refetch } = useQuery({
        queryKey: ['import-ticket', id],
        queryFn: () =>
            getImportTicketDetail({
                token: accessToken,
                ticketId: id as string
            })
    });

    const mapBatchTable = (batches) =>
        batches.map((batch) => ({
            id: batch?.bin?.id,
            productName: batch?.bin?.product,
            productType: batch?.bin?.quality_with_type,
            weight: batch?.in_slot_weight,
            packageType: batch?.bin?.packaged,
            location: batch?.location
        }));

    const handleApproveImportTicket = async () => {
        await approveImportTicket({
            token: accessToken,
            ticketId: id as string
        });
        closeInfoPopup();
        setResultPopup(approveTicketSuccessPopup);
        refetch();
    };

    const handleRemoveDebtConfirm = async () => {
        await removeTicketDebt({
            token: accessToken,
            ticketId: id as string
        });
        closeInfoPopup();
        setResultPopup(removeTicketDebtSuccessPopupData);
        refetch();
    };

    const handleConfirmAction = async () => {
        if (isEqual(infoPopup, approveTicketPopupInfo)) {
            await handleApproveImportTicket();
        }
        if (isEqual(infoPopup, removeTicketDebtPopupData)) {
            await handleRemoveDebtConfirm();
        }
    };

    const openRemoveDebtConfirmPopup = () =>
        setInfoPopup(removeTicketDebtPopupData);

    return (
        <div className="w-full px-5 py-5 relative">
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={handleConfirmAction}
                    cancelAction={closeInfoPopup}
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
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToImportTicketPage}
                />
                <img alt="form-icon" src={warehouseIconSrc} />
                <h1 className="text-xl font-semibold">
                    Phiếu nhập {`/ ${data?.name}`}
                </h1>
            </div>
            {isFetching ? (
                <h1>Loading...</h1>
            ) : (
                <>
                    <ImportTicketDetailMainSection
                        partnerName={data?.partner?.name}
                        partnerPhone={data?.partner?.phone}
                        address={data?.partner?.address?.address_string}
                        dateCreated={data?.createDate}
                        dateApproved={data?.approvedDate}
                        weight={data?.weight}
                        transporter={data?.transporter}
                        price={data?.value}
                        status={data?.status}
                        openPopup={openApproveTicketPopup}
                    />
                    <div className="flex space-x-3 my-5">
                        <Button
                            className={cn(
                                'px-2 py-1 border rounded-md font-semibold w-fit',
                                viewedSection === TicketDetailSection.Debt
                                    ? 'bg-sky-800 text-white'
                                    : 'border-sky-800 text-sky-800'
                            )}
                            text="Công nợ"
                            action={() =>
                                setViewedSection(TicketDetailSection.Debt)
                            }
                        />
                        <Button
                            className={cn(
                                'px-2 py-1 border rounded-md font-semibold w-fit',
                                viewedSection === TicketDetailSection.Batch
                                    ? 'bg-sky-800 text-white'
                                    : 'border-sky-800 text-sky-800'
                            )}
                            text="Lô hàng"
                            action={() =>
                                setViewedSection(TicketDetailSection.Batch)
                            }
                        />
                        <Button
                            className={cn(
                                'px-2 py-1 border rounded-md font-semibold w-fit',
                                viewedSection ===
                                    TicketDetailSection.Description
                                    ? 'bg-sky-800 text-white'
                                    : 'border-sky-800 text-sky-800'
                            )}
                            text="Mô tả"
                            action={() =>
                                setViewedSection(
                                    TicketDetailSection.Description
                                )
                            }
                        />
                    </div>
                    {viewedSection === TicketDetailSection.Batch ? (
                        <TableView
                            columns={batchTableConfig}
                            items={mapBatchTable(data?.bins)}
                        />
                    ) : viewedSection === TicketDetailSection.Description ? (
                        <div className="max-w-[400px]">
                            <DataField
                                name="Mô tả"
                                icon={<GoNote />}
                                disabled={false}
                                value={data?.description}
                                defaultValue="Mô tả"
                            />
                        </div>
                    ) : (
                        data?.debt && (
                            <div className="space-y-5 max-w-[400px]">
                                <DataField
                                    name={'Giá trị'}
                                    icon={<RiMoneyDollarCircleLine />}
                                    disabled={false}
                                    value={`${data?.debt?.value} ${data?.debt?.unit ?? 'VND'}`}
                                    defaultValue={'Giá trị'}
                                />
                                <DataField
                                    name="Ngày hết hạn"
                                    icon={<FaRegCalendarTimes />}
                                    disabled={false}
                                    value={data?.debt?.due_date}
                                    defaultValue="Ngày hết hạn"
                                />
                                <DataField
                                    name="Mô tả"
                                    icon={<GoNote />}
                                    disabled={false}
                                    value={data?.debt?.description}
                                    defaultValue="Mô tả"
                                />
                                <DataField
                                    name="Trạng thái"
                                    icon={<FaCircleNotch />}
                                    disabled={false}
                                    value={
                                        data?.debt?.is_paid
                                            ? 'Đã thanh toán'
                                            : 'Chưa thanh toán'
                                    }
                                    defaultValue={'Trạng thái'}
                                    textColor={
                                        data?.debt?.is_paid
                                            ? 'text-emerald-500'
                                            : 'text-red-500'
                                    }
                                />
                                {!data?.debt?.is_paid && (
                                    <Button
                                        icon={
                                            <MdMoneyOff className="w-[20px] h-[20px]" />
                                        }
                                        className="border border-amber-200 text-amber-200 hover:bg-amber-50 rounded-md"
                                        text="Gạch nợ"
                                        action={openRemoveDebtConfirmPopup}
                                    />
                                )}
                            </div>
                        )
                    )}
                </>
            )}
        </div>
    );
};

export default ImportTicketDetailPage;
